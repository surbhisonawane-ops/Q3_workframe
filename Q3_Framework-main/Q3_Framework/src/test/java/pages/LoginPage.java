package pages;

import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.ConfigReader;

/** FIX (Snippet B / Task 3.2): all locators and interactions live here. */
public class LoginPage extends BasePage {

    private static final Logger log = LogManager.getLogger(LoginPage.class);

    private final By usernameField = By.id("user");
    private final By passwordField = By.id("pass");
    private final By loginButton   = By.id("login");

    public LoginPage(AppiumDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        driver.get(ConfigReader.get("url"));
        return this;
    }

    public LoginPage enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys(username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(password);
        return this;
    }

    public HomePage clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        return new HomePage(driver);
    }

    public HomePage loginAs(String username, String password) {
        log.info("Logging in as user: {}", username);
        return enterUsername(username).enterPassword(password).clickLogin();
    }
}
