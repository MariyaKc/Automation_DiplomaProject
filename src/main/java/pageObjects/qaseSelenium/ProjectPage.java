package pageObjects.qaseSelenium;

import org.openqa.selenium.By;
import org.testng.Assert;
import pageObjects.baseObjects.BasePage;

public class ProjectPage extends BasePage {
    private final By title = By.tagName("h1");

    public ProjectPage() {
        verifyPageUri();
        verifyPageTitle();
    }

    public void verifyPageUri() {
        verifyUri("projects");
    }

    public ProjectPage verifyPageTitle() {
        Assert.assertEquals(getText(title),"Projects" , "Title does not match as expected");
        return this;
    }
}
