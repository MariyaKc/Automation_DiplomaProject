package pageObjects.qaseSelenium;

import entity.Case;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import pageObjects.baseObjects.BasePage;

public class CasePage extends BasePage {

    private final By title = By.tagName("h1");
    private final By inputTitle = By.name("title");
    private final By inputStatus = By.id("0-status");
    private final By inputDescription =By.id("0-description");
    private final By inputSeverity = By.id("0-severity");
    private final By inputPriority = By.id("0-priority");
    private final By inputType = By.id("0-type");
    private final By inputLayer = By.id("0-layer");
    private final By inputIsFlaky = By.id("0-is_flaky");
    private final By inputBehavior = By.id("0-behavior");
    private final By inputAutomationStatus = By.id("0-automation");
    private final By click = By.className("DJXdnf");
    private final By saveBtn = By.id("save-case");
    private final By saveAndCreateBtn = By.xpath("//span[contains(text(),'create')]");
    private final By cancelBtn = By.xpath("//span[contains(text(),'Cancel')]");
    private final By flashMessage = By.className("OL6rtE");

    public CasePage() {
        verifyPageUri();
        verifyPageTitle();
    }

    public void verifyPageUri() {
        verifyUri("case");
    }

    public CasePage verifyPageTitle() {
        Assert.assertEquals(getText(title),"Create test case" , "Title does not match as expected");
        return this;
    }


    public CasePage enterTitle(String name) {
        enter(this.inputTitle, name);
        return this;
    }

    public CasePage enterDescription(String name) {
        enter(this.inputDescription, name);
        return this;
    }

    public CasePage selectStatus(String value) {
        enter(inputStatus, value);
        click(click);
        return this;
    }

    public CasePage selectSeverity(String value) {
        enter(inputSeverity, value);
        click(click);
        return this;
    }

    public CasePage selectPriority(String value) {
        enter(inputPriority, value);
        click(click);
        return this;
    }

    public CasePage selectType(String value) {
        enter(inputType, value);
        click(click);
        return this;
    }

    public CasePage selectLayer(String value) {
        enter(inputLayer, value);
        click(click);
        return this;
    }

    public CasePage selectIsFlaky(String value) {
        enter(inputIsFlaky, value);
        click(click);
        return this;
    }

    public CasePage selectBehavior(String value) {
        enter(inputBehavior, value);
        click(click);
        return this;
    }

    public CasePage selectAutomationStatus(String value) {
        enter(inputAutomationStatus, value);
        click(click);
        return this;
    }

    public CasePage saveAndCreateNewCase() {
        click(saveAndCreateBtn);
        return this;
    }

    public CasePage saveNewCase() {
        click(saveBtn);
        return this;
    }


    public CasePage cancel() {
        click(cancelBtn);
        return this;
    }

    public CasePage enterData(Case cases) {
        enterTitle(cases.getTitle());
        selectStatus(cases.getStatus());
        enterDescription(cases.getDescription());
        selectSeverity(cases.getSeverity());
        selectPriority(cases.getPriority());
        selectType(cases.getType());
        selectLayer(cases.getLayer());
        selectIsFlaky(cases.getIsFlaky());
        selectBehavior(cases.getBehavior());
        selectAutomationStatus(cases.getAutomationStatus());
        return this;
    }

    public CasePage verifyCaseIsCreate() {
        wait.until(ExpectedConditions.presenceOfElementLocated(flashMessage));
        Assert.assertEquals(getText(flashMessage),"Test case was created successfully!" , "Title does not match as expected");
        elementNotExist(flashMessage);
        return this;
    }

}
