package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Properties;

import static propertyHelper.PropertyReader.getProperties;

public class ChromeDriverManager extends DriverManager{
    @Override
    public void createDriver() {
        WebDriver driver;
        Properties properties = getProperties();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(properties.getProperty("browser.configs"));

        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", System.getProperty("user.dir")+ "/files/downloads/");
        chromeOptions.setExperimentalOption("prefs", chromePrefs);

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(chromeOptions);
        webDriver.set(driver);
    }
}
