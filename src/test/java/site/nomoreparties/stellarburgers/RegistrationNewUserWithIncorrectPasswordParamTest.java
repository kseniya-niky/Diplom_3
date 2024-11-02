package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import site.nomoreparties.stellarburgers.api.NewUser;
import site.nomoreparties.stellarburgers.model.LoginPage;
import site.nomoreparties.stellarburgers.model.MainPage;
import site.nomoreparties.stellarburgers.model.RegistrationPage;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class RegistrationNewUserWithIncorrectPasswordParamTest {
    private WebDriver driver;
    @Rule
    public DriverFactory factory = new DriverFactory();
    private MainPage objMainPage;

    private String email, password, name;

    public RegistrationNewUserWithIncorrectPasswordParamTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters
    public static final Object[][] getRegistrationFailInfo() {
        return new Object[][]{
                { Constants.NEW_EMAIL, Constants.PASSWORD_THREE_SYMBOLS, Constants.NEW_NAME },
                { Constants.NEW_EMAIL, Constants.PASSWORD_FIVE_SYMBOLS, Constants.NEW_NAME }
        };
    }

    @Before
    public void startUpBrowser() {
        driver = factory.getDriver();
        driver.get(Constants.BASE_URI);
        objMainPage = new MainPage(driver);
    }

    @Test
    @DisplayName("Регистрация пользователя с паролем меньше 6 символов")
    public void checkRegistrationNewUserWithIncorrectPassword() {
        objMainPage.clickButtonLoginAccount();
        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.clickLinkRegistration();

        RegistrationPage registrationPage = new RegistrationPage(driver);
        NewUser user = new NewUser(email, password, name);
        registrationPage.setUserName(user);
        registrationPage.setUserEmail(user);
        registrationPage.setUserPassword(user);
        registrationPage.clickButtonRegistration();

        assertEquals("Некорректный пароль", registrationPage.getErrorText());
    }
}