package pageObjects.qaseSelenium;

import entity.Project;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import pageObjects.baseObjects.BasePage;

public class ProjectPage extends BasePage {
    private final By title = By.tagName("h1");
    private final By createNewProjectBtn = By.id("createButton");
    private final By inputProjectName = By.id("project-name");
    private final By inputProjectCode = By.id("project-code");
    private final By inputDescription = By.id("description-area");
    private final By createBtn = By.cssSelector("[type='submit']");
    private final By deleteProjectBtn = By.cssSelector(".DRnS3P");
    private final By flashMessagesError = By.className("OL6rtE");
    private final By projectCodeError = By.xpath("//*[@id='project-code']/ancestor::div[@class='prXRTX']//*[@class='Y_SgpC']");

    private WebElement getRadiobutton(String value) {
        return findElement(By.xpath("//*[contains(@type, 'radio') and contains(@value,'"+ value +"')]"));
    }

    private WebElement getProjectName(String name) {
        return findElement(By.xpath("//a[contains(text(),'"+ name +"')]"));
    }

    private WebElement getEllipseProjectForNameBtn(String name) {
        return findElement(By.xpath("//a[contains(text(),'"+ name +"')]/ancestor::tr/td[@class='text-end']"));
    }

    private WebElement deleteProjectForNameBtn(String name) {
        return findElement(By.xpath("//a[contains(text(),'"+ name +"')]/ancestor::tr/td[@class='text-end']//button"));
    }

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

    public ProjectPage createNewProject() {
        click(createNewProjectBtn);
        return this;
    }
    public ProjectPage enterProjectName(String name) {
        enter(this.inputProjectName, name);
        return this;
    }

    public ProjectPage enterProjectCode(String code) {
        enter(this.inputProjectCode, code);
        return this;
    }

    public ProjectPage enterDescription(String name) {
        enter(this.inputDescription, name);
        return this;
    }

    public ProjectPage clickRadiobutton(String value) {
        click(getRadiobutton(value));
        return this;
    }

    public ProjectPage enterData(Project project) {
        enterProjectName(project.getTitle());
        enterProjectCode(project.getCode());
        enterDescription(project.getDescription());
        clickRadiobutton(project.getAccess());
        return this;
    }
    public ProjectPage clickCreate() {
        click(createBtn);
        return this;
    }

    public ProjectPage verifyThenProjectCreate(String name) {
        Assert.assertTrue(getProjectName(name).isDisplayed());
        return this;
    }

    public ProjectPage deleteForName(String name) {
        click(getEllipseProjectForNameBtn(name));
        click(deleteProjectForNameBtn(name));
        click(deleteProjectBtn);
        return this;
    }

    public ProjectPage verifyThenProjectWasDeleted(String name){
        elementNotExist(By.xpath("//a[contains(text(),'"+ name +"')]"));
        return this;
    }

    public ProjectPage verifyFlashMessage() {
        wait.until(ExpectedConditions.presenceOfElementLocated(flashMessagesError));
        Assert.assertEquals(getText(flashMessagesError),"Data is invalid." , "Title does not match as expected");
        return this;
    }

    public ProjectPage verifyProjectCodeError(String error) {
        waitVisibilityOfElement(projectCodeError);
        Assert.assertEquals(getText(projectCodeError), error , "Title does not match as expected");
        return this;
    }


}
