package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import site.nomoreparties.stellarburgers.api.NewUser;
import site.nomoreparties.stellarburgers.api.NewUserRegistrationInfo;
import site.nomoreparties.stellarburgers.api.UserChecks;
import site.nomoreparties.stellarburgers.api.UserClient;
import site.nomoreparties.stellarburgers.model.LoginPage;
import site.nomoreparties.stellarburgers.model.MainPage;
import site.nomoreparties.stellarburgers.model.PersonalAccountPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PersonalAccountTest {
    private WebDriver driver;
    @Rule
    public DriverFactory factory = new DriverFactory();
    private MainPage objMainPage;
    private NewUser currentUser;
    private NewUserRegistrationInfo userRegistrationInfo;
    private UserClient userClient = new UserClient();
    private UserChecks userChecks = new UserChecks();


    @Before
    public void startUpBrowser() {
        driver = factory.getDriver();
        driver.get(Constants.BASE_URI);
        objMainPage = new MainPage(driver);
    }

    @Before
    public void createNewUserByApi() {
        currentUser = NewUser.random();
        ValidatableResponse response = userClient.createNewUser(currentUser);
        userRegistrationInfo = userClient.getResponseAboutNewUser(response);
        userChecks.checkCreatedUser(userRegistrationInfo, currentUser);
    }

    @After
    public void deleteNewUserByApi() {
        if(userRegistrationInfo.getAccessToken() != null) {
            if (!userRegistrationInfo.getAccessToken().isBlank()) {
                ValidatableResponse response = userClient.deleteUser(userRegistrationInfo);
                userChecks.checkDeletedUser(response);
            }
        }
    }

    private String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Test
    @DisplayName("Переход на страницу личного кабинета")
    public void checkOpenPersonalAccount() {
        objMainPage.clickButtonPersonalAccount();

        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.setUserEmail(currentUser);
        objLoginPage.setUserPassword(currentUser);
        objLoginPage.clickButtonLogin();

        objMainPage = new MainPage(driver);
        objMainPage.clickButtonPersonalAccount();

        assertTrue("Открыта не страница личного кабинета", Constants.ACCOUNT_URI.equals(getCurrentUrl()));
    }

    @Test
    @DisplayName("Выход из личного кабинета")
    public void checkClickButtonExit() {
        objMainPage.clickButtonPersonalAccount();

        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.setUserEmail(currentUser);
        objLoginPage.setUserPassword(currentUser);
        objLoginPage.clickButtonLogin();

        objMainPage = new MainPage(driver);
        objMainPage.clickButtonPersonalAccount();

        PersonalAccountPage objPersonalPage = new PersonalAccountPage(driver);
        objPersonalPage.clickButtonExit();

        objLoginPage = new LoginPage(driver);
        objLoginPage.waitTextEntranceToBeVisible();
        assertEquals("Вход", objLoginPage.getExitText());
    }

    @Test
    @DisplayName("Переход на главную страницу при нажатии конструктора в личном кабинете")
    public void checkOpenMainPageByClickingConstructor() {
        objMainPage.clickButtonPersonalAccount();

        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.setUserEmail(currentUser);
        objLoginPage.setUserPassword(currentUser);
        objLoginPage.clickButtonLogin();

        objMainPage = new MainPage(driver);
        objMainPage.clickButtonPersonalAccount();

        PersonalAccountPage objPersonalPage = new PersonalAccountPage(driver);
        objPersonalPage.clickButtonConstructor();

        assertTrue("Открыта не главная страница", Constants.BASE_URI.equals(getCurrentUrl()));
    }

    @Test
    @DisplayName("Переход на главную страницу при нажатии логотипа в личном кабинете")
    public void checkOpenMainPageByClickingLogo() {
        objMainPage.clickButtonPersonalAccount();

        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.setUserEmail(currentUser);
        objLoginPage.setUserPassword(currentUser);
        objLoginPage.clickButtonLogin();

        objMainPage = new MainPage(driver);
        objMainPage.clickButtonPersonalAccount();

        PersonalAccountPage objPersonalPage = new PersonalAccountPage(driver);
        objPersonalPage.clickLogo();

        assertTrue("Открыта не главная страница", Constants.BASE_URI.equals(getCurrentUrl()));
    }
}