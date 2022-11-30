package pageObjects.qaseSelenium;

import entity.Milestone;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pageObjects.baseObjects.BasePage;


public class MilestonePage extends BasePage {
    private final By inputMilestoneName = By.id("title");
    private final By create = By.id("createButton");
    private final By inputDescription = By.cssSelector(".bsD_kN");
    private final By inputStatus = By.id("status");
    private final By click = By.cssSelector(".k6Rjac");
    private final By inputDueData = By.name("dueDate");
    private final By control = By.id("application-content");
    private final By qq = By.className("GBfEhU");
    private final By createMilestoneBtn = By.className("save-button");
    private final By flashMessage = By.cssSelector(".OL6rtE");
    private final By ellipsisBtn = By.className("fa-ellipsis-h");
    private final By deleteBtn = By.className("text-danger");
    private final By confirmDeleteBtn = By.xpath("//span[contains(text(),'Delete')]");

    private WebElement getCalendar(String date) {
        return findElement(By.xpath("//div[contains(text(),'" +date +"')]"));
    }

    private WebElement getEllipseMilestoneForNameBtn(String name) {
        return findElement(By.xpath("//a[contains(text(),'"+ name +"')]/ancestor::tr//a[contains(@class,'btn-dropdown')]"));
    }

    private WebElement deleteMilestoneForNameBtn(String name) {
        return findElement(By.xpath("//a[contains(text(),'" + name + "')]/ancestor::tr//div[@class='dropdown']//a[@class='text-danger']"));
    }


    public MilestonePage create (){
        click(create);
        return this;
    }
    public MilestonePage enterName(String name) {
        enter(this.inputMilestoneName, name);
        return this;
    }

    public MilestonePage enterDescription(String description) {
        enter(this.inputDescription, description);
        return this;
    }

    public MilestonePage enterStatus(String status) {
        enter(this.inputStatus, status);
        click(click);
        return this;
    }

    public MilestonePage enterDue(String date) {
        enter(this.inputDueData, date);
        if(properties.getProperty("browser.configs").contains("headless")){
            enter(this.inputDueData, date);
        } else {
            enter(this.inputDueData, date);
            findElement(control).click();
        }
        return this;
    }


    public MilestonePage enterData(Milestone milestone) {
        enterName(milestone.getMilestoneName());
        enterDescription(milestone.getDescription());
        enterStatus(milestone.getStatus());
        enterDue(milestone.getDueDate());
        return this;
    }

    public MilestonePage saveMilestone() {
        click(createMilestoneBtn);
        return this;
    }

    public MilestonePage verifyFlashMessage() {
        Assert.assertEquals(getText(flashMessage),"Milestone was created successfully!");
        elementNotExist(flashMessage);
        return this;
    }

    public MilestonePage verifyFlashMessageDelete() {
        Assert.assertTrue(getText(flashMessage).contains("was successfully deleted."));
        elementNotExist(flashMessage);
        return this;
    }
    public MilestonePage delete(String name) {
        click(getEllipseMilestoneForNameBtn(name));
        click(deleteMilestoneForNameBtn(name));
        click(confirmDeleteBtn);
        return this;
    }

    public  MilestonePage deleteForCount(int countDeleteProject) {
        for(int i=1; i<=countDeleteProject; i++) {
            retryingFindClick(ellipsisBtn);
            click(deleteBtn);
            click(confirmDeleteBtn);
            verifyFlashMessageDelete();
        }
        return this;
    }
}
