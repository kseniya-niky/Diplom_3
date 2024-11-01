package site.nomoreparties.stellarburgers.model;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static site.nomoreparties.stellarburgers.Constants.WAIT_SECONDS_DURATION;

public class MainPage {
    private WebDriver driver;
    private final By buttonPersonalAccount = By.xpath("//a[@href=\"/account\"]");
    private final By buttonLoginAccount = By.className("button_button__33qZ0");

    private final By tabBuns = By.cssSelector(".tab_tab__1SPyG:nth-child(1)");
    private final By tabSauces = By.cssSelector(".tab_tab__1SPyG:nth-child(2)");
    private final By tabToppings = By.cssSelector(".tab_tab__1SPyG:nth-child(3)");

    private final By imageSpaceSauce = By.cssSelector("img[alt=\"Соус фирменный Space Sauce\"]");
    private final By imageFluorescentBun = By.cssSelector("img[alt=\"Флюоресцентная булка R2-D3\"]");
    private final By imageToppingBeefMeteorite  = By.cssSelector("img[alt=\"Говяжий метеорит (отбивная)\"]");

    private final By frameIngredient = By.cssSelector("a[class^='BurgerIngredient']");


    public MainPage (WebDriver driver) {
        this.driver = driver;
    }

    private void clickElement(By selector) {
        new WebDriverWait(driver, WAIT_SECONDS_DURATION).until(ExpectedConditions.elementToBeClickable(selector));
        driver.findElement(selector).click();
    }

    private boolean waitVisibleElement(By selector) {
        return new WebDriverWait(driver, WAIT_SECONDS_DURATION)
                .until(ExpectedConditions.visibilityOfElementLocated(selector)).isDisplayed();
    }

    @Step("Нажать кнопку \"Личный кабинет\"")
    public void clickButtonPersonalAccount() {
        clickElement(buttonPersonalAccount);
    }

    @Step("Нажать кнопку \"Войти в аккаунт\"")
    public void clickButtonLoginAccount() {
        clickElement(buttonLoginAccount);
    }

    @Step("Нажать вкладку \"Булки\"")
    public void clickTabBuns() {
        clickElement(tabBuns);
    }

    @Step("Нажать вкладку \"Соусы\"")
    public void clickTabSauces() {
        clickElement(tabSauces);
    }

    @Step("Нажать вкладку \"Начинки\"")
    public void clickTabToppings() {
        clickElement(tabToppings);
    }

    public void waitIngredientsToBeShown() {
        new WebDriverWait(driver, WAIT_SECONDS_DURATION)
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(frameIngredient, 2));
    }

    @Step("Проверить, что соусы видны")
    public boolean checkImageSpaceSauceIsVisible() {
        return waitVisibleElement(imageSpaceSauce);
    }

    @Step("Проверить, что булки видны")
    public boolean checkImageFluorescentBunIsVisible() {
        return waitVisibleElement(imageFluorescentBun);
    }

    @Step("Проверить, что начинки видны")
    public boolean checkImageToppingBeefMeteoriteIsVisible() {
       return waitVisibleElement(imageToppingBeefMeteorite);
    }
}