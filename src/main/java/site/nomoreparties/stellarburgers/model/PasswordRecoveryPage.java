package site.nomoreparties.stellarburgers.model;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static site.nomoreparties.stellarburgers.Constants.WAIT_SECONDS_DURATION;

public class PasswordRecoveryPage {
    private WebDriver driver;
    private final By linkEnter = By.xpath("//a[@class=\"Auth_link__1fOlj\" and text()=\"Войти\"]");

    public PasswordRecoveryPage(WebDriver driver) {
        this.driver = driver;
    }

    private void clickElement(By selector) {
        new WebDriverWait(driver, WAIT_SECONDS_DURATION).until(ExpectedConditions.elementToBeClickable(selector));
        driver.findElement(selector).click();
    }

    @Step("Нажать ссылку \"Войти\"")
    public void clickLinkEnter() {
        clickElement(linkEnter);
    }
}