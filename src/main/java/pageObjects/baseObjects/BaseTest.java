package pageObjects.baseObjects;

import com.codeborne.selenide.Configuration;
import driver.DriverManagerFactory;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import lombok.extern.log4j.Log4j;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import testNgUtils.ExtentReportListener;
import testNgUtils.InvokedMethodListener;
import testNgUtils.Listener;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import static driver.DriverManagerFactory.*;
import static driver.DriverManager.*;
import static propertyHelper.PropertyReader.getProperties;

// класс содержит методы, которые могут быть многократно использованы в конкретных классах страниц
@Listeners({Listener.class, InvokedMethodListener.class, ExtentReportListener.class})
@Log4j
public abstract class BaseTest {
    protected Properties properties;

    @BeforeTest
    public void setUp(ITestContext context) {
        log.debug("I started new wed driver!");
        properties = getProperties();
        getManager(DriverManagerType.valueOf(properties.containsKey("browser") ? properties.getProperty("browser").toUpperCase() : "CHROME"));
    }

    protected <T> T get(Class<T> page) {
        T instance = null;
        try {
            instance = page.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

    @AfterTest
    public void stop() {
        log.debug("I close wed driver!");
        closeWebDriver();
    }
}
