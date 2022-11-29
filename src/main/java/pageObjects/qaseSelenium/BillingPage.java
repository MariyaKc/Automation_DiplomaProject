package pageObjects.qaseSelenium;

import org.openqa.selenium.By;
import org.testng.Assert;
import pageObjects.baseObjects.BasePage;

public class BillingPage extends BasePage {

    private final By title = By.tagName("h1");

    public BillingPage() {
        verifyPageUri();
    }

    public void verifyPageUri() {
        verifyUri("billing");
    }

    public BillingPage verifyBuildingPage() {
        Assert.assertEquals(getText(title), "Subscriptions");
        return this;
    }
}
