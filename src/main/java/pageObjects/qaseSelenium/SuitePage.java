package pageObjects.qaseSelenium;

import entity.Suite;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import pageObjects.baseObjects.BasePage;

public class SuitePage extends BasePage {

    private final By title = By.tagName("h1");
    private final By inputSuiteName = By.className("QJEchT");
    private final By inputDescription = By.id("description");
    private final By inputPreconditions = By.id("preconditions");
    private final By createBtn = By.id("save-suite-button");
    private final By flashMessage = By.className("OL6rtE");

    public SuitePage enterSuiteName(String name) {
        enter(this.inputSuiteName, name);
        return this;
    }

    public SuitePage enterDescription(String text) {
        enter(this.inputDescription, text);
        return this;
    }

    public SuitePage enterPreconditions(String text) {
        enter(this.inputPreconditions, text);
        return this;
    }

    public SuitePage createSuite() {
        click(createBtn);
        return this;
    }

    public SuitePage enterData(Suite suite) {
        enterSuiteName(suite.getSuiteName());
        enterDescription(suite.getDescription());
        enterPreconditions(suite.getPreconditions());
        return this;
    }

    public SuitePage verifySuiteIsCreate() {
        wait.until(ExpectedConditions.presenceOfElementLocated(flashMessage));
        Assert.assertEquals(getText(flashMessage),"Suite was successfully created." , "Title does not match as expected");
        elementNotExist(flashMessage);
        return this;
    }
}
