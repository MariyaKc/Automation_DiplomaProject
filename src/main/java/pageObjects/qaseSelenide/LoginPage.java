package pageObjects.qaseSelenide;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import pageObjects.baseObjects.SelenideBasePage;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends SelenideBasePage {
    private final SelenideElement emailInput = $("#inputEmail");
    private final SelenideElement passwordInput = $("#inputPassword");
    private final SelenideElement loginBtn = $("#btnLogin");


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

}
