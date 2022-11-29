package pageObjects.qaseSelenium;

import org.openqa.selenium.By;
import pageObjects.baseObjects.BasePage;

public class HomePage extends BasePage {

    private final By logo = By.cssSelector("[alt='Qase logo']");
    private final By loginBtn = By.id("signin");
    private final By startForFreeBtn = By.id("signup");


    public HomePage open() {
        load();
        return this;
    }

    public HomePage verifyHomePageIsOpened() {
        waitVisibilityOfElements(logo, startForFreeBtn, loginBtn);
        return this;
    }

    public HomePage clickLogin() {
        click(loginBtn);
        return this;
    }

    public HomePage clickStartForFree() {
        click(startForFreeBtn);
        return this;
    }

}
