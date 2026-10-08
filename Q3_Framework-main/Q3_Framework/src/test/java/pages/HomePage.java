package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    private final By welcomeBanner = By.id("welcome");

    public HomePage(AppiumDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeBanner)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
