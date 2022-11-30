package cucumberSteps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import pageObjects.baseObjects.BaseTest;
import pageObjects.qaseSelenium.HomePage;
import pageObjects.qaseSelenium.LoginPage;

public class LoginSteps extends BaseTest {

    @Given("I open home page and click login")
    public void open() {
        get(HomePage.class).open();
        get(HomePage.class).clickLogin();

    }

    @When("I verify, then login page was opened and i enter email and password")
    public void login() {
        get(LoginPage.class) .enterEmail("test@dispostable.com").enterPassword("1234567QA");
    }

    @And("I click login button")
    public void clickLogin(){
        get(LoginPage.class) .clickLogin();

    }
}
