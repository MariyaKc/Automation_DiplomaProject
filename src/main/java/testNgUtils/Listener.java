package testNgUtils;

import driver.DriverManager;
import driver.DriverManagerFactory;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import lombok.SneakyThrows;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import propertyHelper.PropertyReader;

import java.util.Properties;

import static propertyHelper.PropertyReader.getProperties;


/**Listener для TestNG Report и для получения параметра для работы с property*/
public class Listener implements ITestListener {
    protected Properties properties;

    @SneakyThrows
    @Override
    public void onStart(ITestContext context) {
        String propertyName = context.getSuite().getParameter("config") == null ? System.getProperty("config") : context.getSuite().getParameter("config");
        new PropertyReader(propertyName);
        properties = getProperties();
        DriverManagerFactory.getManager(DriverManagerType.valueOf(properties.containsKey("browser") ? properties.getProperty("browser").toUpperCase() : "CHROME"));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Reporter.log("Ohh... this test was failed => " + result.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        Reporter.log(context.getSuite().getXmlSuite().getTest());
        DriverManager.closeWebDriver();
    }

}
