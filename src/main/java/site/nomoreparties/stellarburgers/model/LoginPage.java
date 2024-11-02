package site.nomoreparties.stellarburgers.model;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import site.nomoreparties.stellarburgers.api.NewUser;

import static site.nomoreparties.stellarburgers.Constants.WAIT_SECONDS_DURATION;

public class LoginPage {
    private WebDriver driver;
    private final By fieldEmail = By.name("name");
    private final By fieldPassword = By.name("Пароль");

    private final By buttonLogin = By.className("button_button__33qZ0");

    private final By linkRegistration =
            By.xpath("//a[@class=\"Auth_link__1fOlj\" and text()=\"Зарегистрироваться\"]");

    private final By linkRecoverPassword =
            By.xpath("//a[@class=\"Auth_link__1fOlj\" and text()=\"Восстановить пароль\"]");

    private final By textEntrance = By.xpath("//div[@class=\"Auth_login__3hAey\"]/h2[text()=\"Вход\"]");

    public LoginPage (WebDriver driver) {
        this.driver = driver;
    }

    private void clickElement(By selector) {
        new WebDriverWait(driver, WAIT_SECONDS_DURATION).until(ExpectedConditions.elementToBeClickable(selector));
        driver.findElement(selector).click();
    }

    private void fillField(By selector, String text) {
        new WebDriverWait(driver, WAIT_SECONDS_DURATION).until(ExpectedConditions.elementToBeClickable(selector));
        clickElement(selector);
        driver.findElement(selector).clear();
        driver.findElement(selector).sendKeys(text);
    }

    private boolean waitVisibleElement(By selector) {
        return new WebDriverWait(driver, WAIT_SECONDS_DURATION)
                .until(ExpectedConditions.visibilityOfElementLocated(selector)).isDisplayed();
    }

    @Step("Ввод email")
    public void setUserEmail(NewUser user) {
        fillField(fieldEmail, user.getEmail());
    }

    @Step("Ввод пароля")
    public void setUserPassword(NewUser user) {
        fillField(fieldPassword, user.getPassword());
    }

    @Step("Нажать кнопку \"Войти\"")
    public void clickButtonLogin() {
        clickElement(buttonLogin);
    }

    @Step("Нажать ссылку \"Зарегистрироваться\"")
    public void clickLinkRegistration() {
        clickElement(linkRegistration);
    }

    @Step("Нажать ссылку \"Восстановить пароль\"")
    public void clickLinkRecoverPassword() {
        clickElement(linkRecoverPassword);
    }

    @Step("Отображается текст \"Вход\"")
    public void waitTextEntranceToBeVisible() {
        waitVisibleElement(textEntrance);
    }

    public String getExitText() {
        return driver.findElement(textEntrance).getText();
    }
}