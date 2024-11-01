package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import site.nomoreparties.stellarburgers.api.NewUser;
import site.nomoreparties.stellarburgers.api.NewUserRegistrationInfo;
import site.nomoreparties.stellarburgers.api.UserChecks;
import site.nomoreparties.stellarburgers.api.UserClient;
import site.nomoreparties.stellarburgers.model.*;

import static org.junit.Assert.assertEquals;

public class LoginUserTest {
    private WebDriver driver;
    @Rule
    public DriverFactory factory = new DriverFactory();
    private MainPage objMainPage;
    private LocalStorage localStorage;
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
        if(localStorage.getItem("accessToken") != null) {
            if (!localStorage.getItem("accessToken").isBlank()) {
                ValidatableResponse response = userClient.deleteUser(userRegistrationInfo);
                userChecks.checkDeletedUser(response);
            }
        }
    }

    @Test
    @DisplayName("Вход пользователя по кнопке «Войти в аккаунт» на главной странице")
    public void checkLoginUserUsingButtonLoginAccountOnMainPage() {
        objMainPage.clickButtonLoginAccount();

        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.setUserEmail(currentUser);
        objLoginPage.setUserPassword(currentUser);
        objLoginPage.clickButtonLogin();

        objMainPage = new MainPage(driver);
        objMainPage.clickButtonPersonalAccount();

        PersonalAccountPage objPersonalPage = new PersonalAccountPage(driver);
        localStorage = ((WebStorage) driver).getLocalStorage();

        assertEquals("Вход в систему выполнен под другим пользователем",
                currentUser.getName(), objPersonalPage.getFieldName());
    }

    @Test
    @DisplayName("Вход пользователя по кнопке «Личный кабинет» на главной странице")
    public void checkLoginUserUsingButtonPersonalAccountOnMainPage() {
        objMainPage.clickButtonPersonalAccount();

        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.setUserEmail(currentUser);
        objLoginPage.setUserPassword(currentUser);
        objLoginPage.clickButtonLogin();

        objMainPage = new MainPage(driver);
        objMainPage.clickButtonPersonalAccount();

        PersonalAccountPage objPersonalPage = new PersonalAccountPage(driver);
        localStorage = ((WebStorage) driver).getLocalStorage();
        assertEquals("Вход в систему выполнен под другим пользователем",
                currentUser.getName(), objPersonalPage.getFieldName());
    }

    @Test
    @DisplayName("Вход пользователя на форме регистрации")
    public void checkLoginUserUsingButtonLoginOnRegistrationPage() {
        objMainPage.clickButtonPersonalAccount();

        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.clickLinkRegistration();

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.scrollToLinkEnter();
        registrationPage.clickLinkEnter();

        objLoginPage = new LoginPage(driver);
        objLoginPage.setUserEmail(currentUser);
        objLoginPage.setUserPassword(currentUser);
        objLoginPage.clickButtonLogin();

        objMainPage = new MainPage(driver);
        objMainPage.clickButtonPersonalAccount();

        PersonalAccountPage objPersonalPage = new PersonalAccountPage(driver);
        localStorage = ((WebStorage) driver).getLocalStorage();
        assertEquals("Вход в систему выполнен под другим пользователем",
                currentUser.getName(), objPersonalPage.getFieldName());
    }

    @Test
    @DisplayName("Вход пользователя на форме восстановления пароля")
    public void checkLoginUserUsingButtonLoginOnPasswordRecoveryPage() {
        objMainPage.clickButtonLoginAccount();

        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.clickLinkRecoverPassword();

        PasswordRecoveryPage passwordRecoveryPage = new PasswordRecoveryPage(driver);
        passwordRecoveryPage.clickLinkEnter();

        objLoginPage = new LoginPage(driver);
        objLoginPage.setUserEmail(currentUser);
        objLoginPage.setUserPassword(currentUser);
        objLoginPage.clickButtonLogin();

        objMainPage = new MainPage(driver);
        objMainPage.clickButtonPersonalAccount();

        PersonalAccountPage objPersonalPage = new PersonalAccountPage(driver);
        localStorage = ((WebStorage) driver).getLocalStorage();
        assertEquals("Вход в систему выполнен под другим пользователем",
                currentUser.getName(), objPersonalPage.getFieldName());
    }
}