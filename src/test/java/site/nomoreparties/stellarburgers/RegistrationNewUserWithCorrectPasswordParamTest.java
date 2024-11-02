package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import site.nomoreparties.stellarburgers.api.NewUser;
import site.nomoreparties.stellarburgers.api.UserChecks;
import site.nomoreparties.stellarburgers.api.UserClient;
import site.nomoreparties.stellarburgers.model.LoginPage;
import site.nomoreparties.stellarburgers.model.MainPage;
import site.nomoreparties.stellarburgers.model.PersonalAccountPage;
import site.nomoreparties.stellarburgers.model.RegistrationPage;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class RegistrationNewUserWithCorrectPasswordParamTest {
    private WebDriver driver;
    @Rule
    public DriverFactory factory = new DriverFactory();
    private MainPage objMainPage;
    private UserClient userClient = new UserClient();
    private UserChecks userChecks = new UserChecks();
    private  LocalStorage localStorage;

    private String email, password, name;

    public RegistrationNewUserWithCorrectPasswordParamTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters
    public static final Object[][] getRegistrationInfo() {
        return new Object[][]{
                { Constants.NEW_EMAIL, Constants.PASSWORD_SIX_SYMBOLS, Constants.NEW_NAME },
                { Constants.NEW_EMAIL, Constants.PASSWORD_SEVEN_SYMBOLS, Constants.NEW_NAME },
                { Constants.NEW_EMAIL, Constants.PASSWORD_FIFTEEN_SYMBOLS, Constants.NEW_NAME }
        };
    }

    @Before
    public void startUpBrowser() {
        driver = factory.getDriver();
        driver.get(Constants.BASE_URI);
        objMainPage = new MainPage(driver);
    }

    @Test
    @DisplayName("Регистрация пользователя (с паролем 6 и более символов)")
    public void checkRegistrationNewUser() {
        objMainPage.clickButtonLoginAccount();
        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.clickLinkRegistration();

        RegistrationPage registrationPage = new RegistrationPage(driver);
        NewUser user = new NewUser(email, password, name);
        registrationPage.setUserName(user);
        registrationPage.setUserEmail(user);
        registrationPage.setUserPassword(user);
        registrationPage.clickButtonRegistration();

        objLoginPage = new LoginPage(driver);
        objLoginPage.waitTextEntranceToBeVisible();
        objLoginPage.setUserEmail(user);
        objLoginPage.setUserPassword(user);
        objLoginPage.clickButtonLogin();

        objMainPage = new MainPage(driver);
        objMainPage.clickButtonPersonalAccount();

        PersonalAccountPage objPersonalPage = new PersonalAccountPage(driver);
        localStorage = ((WebStorage) driver).getLocalStorage();

        assertEquals("Пользователь не зарегистрирован", user.getName(), objPersonalPage.getFieldName());
    }

    @After
    public void deleteNewUserByApi() {
        if(localStorage.getItem("accessToken") != null) {
            if (!localStorage.getItem("accessToken").isBlank()) {
                ValidatableResponse response = userClient.deleteUser(localStorage);
                userChecks.checkDeletedUser(response);
            }
        }
    }
}