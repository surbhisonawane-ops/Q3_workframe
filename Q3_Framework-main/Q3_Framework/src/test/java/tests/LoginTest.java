package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.DriverFactory;

public class LoginTest extends BaseTest {

    @Test
    public void testLogin() {
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver()).open();
        HomePage home = loginPage.loginAs(ConfigReader.get("username"),
                                          ConfigReader.get("password"));
        Assert.assertTrue(home.isDisplayed(), "Home page not displayed after login");
    }
}
