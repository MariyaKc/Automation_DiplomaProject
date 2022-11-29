package WEB_Tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.baseObjects.BaseTest;
import pageObjects.qaseSelenium.HomePage;
import pageObjects.qaseSelenium.SignUpPage;
import entity.SignUp;

public class SignUp_Test extends BaseTest {

    @BeforeTest
    public void preconditions() {
        get(HomePage.class)
                .open().verifyHomePageIsOpened().clickStartForFree();
    }

    @Test(description = "Sign Up test with wrong in one of the required fields", dataProvider = "signup data")
    public void negativeSignUpTest(String email, String password, String confirmPassword, Boolean check, String emailErr, String passwordErr) {

        SignUp signUp = new SignUp.SignUpBuilder()
                .withEmail(email)
                .withPassword(password)
                .withConfirmPassword(confirmPassword)
                .withCheckbox(check).create();

        get(SignUpPage.class).enterData(signUp).createAccount();

        if (signUp.getCheckbox() == false) {
            get(SignUpPage.class).checkEmailError(emailErr).checkPasswordError(passwordErr).checkCheckboxError();
        } else if (signUp.getCheckbox() == true) {
            get(SignUpPage.class).checkEmailError(emailErr).checkPasswordError(passwordErr);
        }
    }

    @DataProvider(name = "signup data")
    public Object [][] data(){
        return new Object[][] {
                {"testtest@dispostable.com", "12345678", "1234", true, "", "The password confirmation does not match."},
                {properties.getProperty("email"), "test", "test", false, "The email has already been taken.", "The password must be at least 8 characters."},
                {"testtest@dispostable.com", "12345678", "12345678", false, "", ""},
        };
    }
}

