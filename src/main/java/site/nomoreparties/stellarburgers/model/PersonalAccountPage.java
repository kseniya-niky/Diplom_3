package site.nomoreparties.stellarburgers.model;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static site.nomoreparties.stellarburgers.Constants.WAIT_SECONDS_DURATION;

public class PersonalAccountPage {
    private WebDriver driver;
    private final By fieldName = By.xpath("//input[@name=\"Name\"]");
    private final By buttonExit = By.xpath("//button[text()=\"Выход\"]");
    private final By buttonConstructor = By.xpath("//p[text() = 'Конструктор']");
    private final By logo = By.cssSelector(".AppHeader_header__logo__2D0X2 svg");

    public PersonalAccountPage(WebDriver driver) {
        this.driver = driver;
    }

    private void clickElement(By selector) {
        new WebDriverWait(driver, WAIT_SECONDS_DURATION).until(ExpectedConditions.elementToBeClickable(selector));
        driver.findElement(selector).click();
    }

    @Step("Получить имя пользователя")
    public String getFieldName() {
        new WebDriverWait(driver, WAIT_SECONDS_DURATION).until(ExpectedConditions.visibilityOfElementLocated(fieldName));
        return driver.findElement(fieldName).getAttribute("value");
    }

    @Step("Нажать кнопку \"Выход\"")
    public void clickButtonExit() {
        clickElement(buttonExit);
    }

    @Step("Нажать кнопку \"Конструктор\"")
    public void clickButtonConstructor() {
        clickElement(buttonConstructor);
    }

    @Step("Нажать логотип")
    public void clickLogo() {
        clickElement(logo);
    }
}