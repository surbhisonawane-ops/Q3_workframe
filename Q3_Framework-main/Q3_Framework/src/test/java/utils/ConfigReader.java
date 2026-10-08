package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * FIX (Snippet D / Task 3.3):
 *  - loads from classpath (works with mvn test, CI, any working dir)
 *  - try-with-resources closes the stream
 *  - exceptions handled and logged (ERROR on failure, INFO on success)
 *  - loaded once, not on every call
 */
public final class ConfigReader {

    private static final Logger log = LogManager.getLogger(ConfigReader.class);
    private static final String CONFIG_FILE = "config.properties";
    private static final Properties props = new Properties();

    static {
        loadProperties();
    }

    private ConfigReader() { }

    private static void loadProperties() {
        try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (in == null) {
                throw new FileNotFoundException(CONFIG_FILE + " not found on classpath");
            }
            props.load(in);
            log.info("Loaded {} with {} properties", CONFIG_FILE, props.size());
        } catch (FileNotFoundException e) {
            log.error("Configuration file missing: {}", CONFIG_FILE, e);
            throw new IllegalStateException("Configuration file missing: " + CONFIG_FILE, e);
        } catch (IOException e) {
            log.error("Failed to read {}", CONFIG_FILE, e);
            throw new IllegalStateException("Failed to read " + CONFIG_FILE, e);
        }
    }

    public static String get(String key) {
        String value = props.getProperty(key);
        if (value == null) {
            log.warn("Property '{}' not found in {}", key, CONFIG_FILE);
        } else {
            log.debug("Read property '{}'", key);
        }
        return value;
    }

    public static String getUrl() {
        return get("url");
    }
}
