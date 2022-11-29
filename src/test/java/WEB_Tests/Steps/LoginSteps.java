package WEB_Tests.Steps;

import io.qameta.allure.Step;
import pageObjects.baseObjects.BasePage;
import pageObjects.qaseSelenium.HomePage;
import pageObjects.qaseSelenium.LoginPage;

public class LoginSteps extends BasePage {

    HomePage homePage = new HomePage();

    @Step("Login by user:: {username}, {password}")
    public LoginSteps login(){
        homePage.open().verifyHomePageIsOpened().clickLogin();
        new LoginPage()
                .enterEmail(properties.getProperty("email")).enterPassword(properties.getProperty("password"))
                .clickLogin();
        return this;
    }
}
