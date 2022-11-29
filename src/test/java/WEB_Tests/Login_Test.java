package WEB_Tests;

import org.testng.annotations.*;
import pageObjects.baseObjects.BaseTest;
import pageObjects.qaseSelenium.HomePage;
import pageObjects.qaseSelenium.LoginPage;
import pageObjects.qaseSelenium.ProjectPage;

import java.lang.reflect.Method;

public class Login_Test extends BaseTest {

    @BeforeTest
    public void preconditions() {
        get(HomePage.class)
                .open().verifyHomePageIsOpened().clickLogin();
    }

    @Test(description = "Test with correct credentials :: {email}, {password}", dataProvider = "user data")
    public void positiveLoginTest(String email, String password) {
        get(LoginPage.class)
                .enterEmail(email).enterPassword(password)
                .clickLogin();
        get(ProjectPage.class).verifyPageTitle();
    }

    @Test(description = "Test with incorrect credentials :: {email}, {password}", dataProvider = "user data")
    public void negativeLoginTest(String email, String password, String errorMessage) {
        get(LoginPage.class)
                .enterEmail(email).enterPassword(password)
                .clickLogin()
                .checkErrorMessage(errorMessage);
    }

    @DataProvider(name = "user data")
    public Object[][] data(Method method) {
        Object[][] result = null;

        if (method.getName().equals("negativeLoginTest")) {
            result = new Object[][]{
                {properties.getProperty("email"), "18765432", "These credentials do not match our records."},
                {"test@test.ru", properties.getProperty("password"), "These credentials do not match our records."}};

        } else if (method.getName().equals("positiveLoginTest")) {
            result =  new Object[][]{
                    {properties.getProperty("email"), properties.getProperty("password")}
            };
        }
        return result;
    }
}
