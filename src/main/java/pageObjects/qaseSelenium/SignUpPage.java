package pageObjects.qaseSelenium;

import org.openqa.selenium.By;
import org.testng.Assert;
import pageObjects.baseObjects.BasePage;
import entity.SignUp;

public class SignUpPage extends BasePage {

    private final By emailInput = By.id("inputEmail");
    private final By passwordInput = By.id("inputPassword");
    private final By confirmPasswordInput = By.name("password_confirmation");
    private final By createAccountBtn = By.xpath("//button");
    private final By checkbox = By.className("custom-control-indicator");
    private final By flag = By.xpath("//input[@name = 'agreement']");
    private final By emailError = By.xpath("//div[@class='form-group has-error']/child::div");
    private final By passwordError = By.xpath("//div[@class='form-group']/child::div");
    private final By checkboxError = By.className("checkbox-feedback");


    public SignUpPage() {
        verifyPageUri();
        verifyLoginPage();
    }

    public void verifyPageUri() {
        verifyUri("signup");
    }

    public SignUpPage verifyLoginPage() {
        waitVisibilityOfElements(emailInput, passwordInput, confirmPasswordInput, createAccountBtn);
        return this;
    }

    public SignUpPage enterEmail(String email) {
        enter(this.emailInput, email);
        return this;
    }

    public SignUpPage enterPassword(String password) {
        enter(this.passwordInput, password);
        return this;
    }

    public SignUpPage enterConfirmPassword(String confirmPassword) {
        enter(this.confirmPasswordInput, confirmPassword);
        return this;
    }

    public SignUpPage createAccount() {
        click(createAccountBtn);
        return this;
    }

    public SignUpPage enterData(SignUp signUp) {
        enterEmail(signUp.getEmail());
        enterPassword(signUp.getPassword());
        enterConfirmPassword(signUp.getConfirmPassword());
        if ((signUp.getCheckbox()==true & findElement(flag).isSelected()==false) || (signUp.getCheckbox()==false & findElement(flag).isSelected()==true)) {
           click(checkbox);
        }
        createAccount();
        return this;
    }

    public SignUpPage checkEmailError(String error) {
        if(elementExist(emailError)==true) {
            Assert.assertEquals(getText(emailError), error, "Error does not match as expected");
        }
        return this;
    }

    public SignUpPage checkPasswordError(String error) {
        if(elementExist(passwordError)==true) {
            Assert.assertEquals(getText(passwordError), error, "Error does not match as expected");
        }
        return this;
    }

    public SignUpPage checkCheckboxError() {
            Assert.assertEquals(getText(checkboxError), "The agreement field is required.", "Error does not match as expected");
        return this;
    }
}
