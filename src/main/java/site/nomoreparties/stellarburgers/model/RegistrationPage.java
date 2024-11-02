package site.nomoreparties.stellarburgers.model;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import site.nomoreparties.stellarburgers.api.NewUser;

import static site.nomoreparties.stellarburgers.Constants.WAIT_SECONDS_DURATION;

public class RegistrationPage {
    private WebDriver driver;
    private final By fieldName = By.cssSelector(".Auth_fieldset__1QzWN:nth-child(1) input.input__textfield");
    private final By fieldEmail = By.cssSelector(".Auth_fieldset__1QzWN:nth-child(2) input.input__textfield");
    private final By fieldPassword = By.cssSelector(".Auth_fieldset__1QzWN:nth-child(3) input.input__textfield");
    private final By buttonRegistration = By.className("button_button__33qZ0");
    private final By linkEnter = By.xpath("//a[@class=\"Auth_link__1fOlj\" and text()=\"Войти\"]");

    private final By errorText = By.className("input__error");

    public RegistrationPage (WebDriver driver) {
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

    @Step("Нажать ссылку \"Войти\"")
    public void clickLinkEnter() {
        clickElement(linkEnter);
    }

    public void scrollToLinkEnter() {
        WebElement link = driver.findElement(linkEnter);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", link);
    }

    @Step("Ввод имени нового пользователя")
    public void setUserName(NewUser user) {
        fillField(fieldName, user.getName());
    }

    @Step("Ввод email нового пользователя")
    public void setUserEmail(NewUser user) {
        fillField(fieldEmail, user.getEmail());
    }

    @Step("Ввод пароля нового пользователя")
    public void setUserPassword(NewUser user) {
        fillField(fieldPassword, user.getPassword());
    }

    @Step("Нажать кнопку \"Зарегистрироваться\"")
    public void clickButtonRegistration() {
        clickElement(buttonRegistration);
    }

    @Step("Получить текст ошибки")
    public String getErrorText() {
        return driver.findElement(errorText).getText();
    }
}