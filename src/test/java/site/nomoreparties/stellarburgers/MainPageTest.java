package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import site.nomoreparties.stellarburgers.model.LoginPage;
import site.nomoreparties.stellarburgers.model.MainPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MainPageTest {
    private WebDriver driver;
    @Rule
    public DriverFactory factory = new DriverFactory();
    private MainPage objMainPage;

    @Before
    public void startUpBrowser() {
        driver = factory.getDriver();
        driver.get(Constants.BASE_URI);
        objMainPage = new MainPage(driver);
    }

    @Test
    @DisplayName("Проверить нажатие кнопки Личный кабинет")
    public void checkButtonPersonalAccount() {
        objMainPage.clickButtonPersonalAccount();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitTextEntranceToBeVisible();
        assertEquals("Вход", loginPage.getExitText());
    }

    @Test
    @DisplayName("Проверить нажатие вкладок с ингредиентами")
    public void checkClickableTabsOfIngredients() {
        objMainPage.waitIngredientsToBeShown();
        objMainPage.clickTabSauces();
        assertTrue(objMainPage.checkImageSpaceSauceIsVisible());

        objMainPage.clickTabBuns();
        assertTrue(objMainPage.checkImageFluorescentBunIsVisible());

        objMainPage.clickTabToppings();
        assertTrue(objMainPage.checkImageToppingBeefMeteoriteIsVisible());
    }
}