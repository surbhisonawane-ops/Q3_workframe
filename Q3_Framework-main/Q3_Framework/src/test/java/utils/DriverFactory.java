package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;

public final class DriverFactory {

    private static final Logger log = LogManager.getLogger(DriverFactory.class);
    private static final ThreadLocal<AppiumDriver> driverThread = new ThreadLocal<>();

    private DriverFactory() { }

    public static void initDriver() {
        try {
            UiAutomator2Options options = new UiAutomator2Options()
                    .setPlatformName("Android")
                    .setDeviceName(ConfigReader.get("device.name"))
                    .withBrowserName("Chrome");
            driverThread.set(new AndroidDriver(new URL(ConfigReader.get("appium.url")), options));
            log.info("Driver initialised on thread {}", Thread.currentThread().getId());
        } catch (MalformedURLException e) {
            log.error("Invalid appium.url in config.properties", e);
            throw new IllegalStateException("Invalid appium.url", e);
        }
    }

    public static AppiumDriver getDriver() {
        AppiumDriver driver = driverThread.get();
        if (driver == null) {
            throw new IllegalStateException("Driver not initialised for this thread. Call initDriver() first.");
        }
        return driver;
    }

    public static void quitDriver() {
        AppiumDriver driver = driverThread.get();
        if (driver != null) {
            driver.quit();
            log.info("Driver quit on thread {}", Thread.currentThread().getId());
        }
        driverThread.remove();
    }
}
