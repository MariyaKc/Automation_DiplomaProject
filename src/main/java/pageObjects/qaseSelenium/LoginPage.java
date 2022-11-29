package pageObjects.qaseSelenium;

import org.openqa.selenium.By;
import org.testng.Assert;
import pageObjects.baseObjects.BasePage;

public class LoginPage extends BasePage {

    private final By emailInput = By.id("inputEmail");
    private final By passwordInput = By.id("inputPassword");
    private final By loginBtn = By.id("btnLogin");
    private final By errorMessage = By.className("form-control-feedback");

    public LoginPage() {
        verifyPageUri();
        verifyLoginPage();
    }

    public void verifyPageUri() {
        verifyUri("login");
    }

    public LoginPage verifyLoginPage() {
        waitVisibilityOfElements(emailInput, passwordInput, loginBtn);
        return this;
    }

    public LoginPage enterEmail(String email) {
        enter(this.emailInput, email);
        return this;
    }

    public LoginPage enterPassword(String password) {
        enter(this.passwordInput, password);
        return this;
    }

    public LoginPage clickLogin() {
        click(loginBtn);
        return this;
    }

    public LoginPage checkErrorMessage(String error) {
        Assert.assertEquals(getText(errorMessage), error, "Error does not match as expected");
        return this;
    }

}
