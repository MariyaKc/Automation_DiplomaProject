package pageObjects.qaseSelenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import pageObjects.baseObjects.SelenideBasePage;

import static com.codeborne.selenide.Selenide.$;

public class HomePage extends SelenideBasePage {

    private final SelenideElement logo = $("[alt='Qase logo']");
    private final SelenideElement loginBtn = $("#signin");

    public HomePage(){
        logo.shouldBe(Condition.visible);
        loginBtn.shouldBe(Condition.visible);
    }

    public HomePage clickLogin(){
        click(loginBtn);
        return this;
    }
}
