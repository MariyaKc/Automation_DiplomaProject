package pageObjects.qaseSelenium;

import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import pageObjects.baseObjects.BasePage;

import java.io.File;

@Log4j
public class RepositoryPage extends BasePage {
    private final By title = By.xpath("//h1[@class='VqrSGU']");
    private final By ellipsisBtn = By.className("fa-ellipsis-h");
    private final By importDataBtn = By.cssSelector(".fa-download");
    private final By exportDataBtn = By.cssSelector(".fa-upload");
    private final By createNewCase =By.xpath("//span[contains(text(),' new case')]");
    private final By createNewSuite =By.id("create-suite-button");
    private final By chooseFile = By.xpath("//input[@type='file']");
    private final By importBtn = By.xpath("//*[@id='modals']//button[contains(@class,'tscvgR')]");
    private final By exportBtn = By.xpath("//button[contains(@class,'btn-primary')]");
    private final By flashMessage = By.className("OL6rtE");
    private final By suiteTitle = By.className("FhcTGD");
    private final By deleteSuiteBtn = By.xpath("//i[contains(@class,'fa-trash')]");
    private final By selectAllCases = By.xpath("//div[@class='ioDlVH']//input");
    private final By deleteCaseBtn = By.xpath("//button[@class='btn btn-secondary me-2']");
    private final By inputConfirm = By.name("confirm");
    private final By confirmDeleteBtn = By.xpath("//span[@class='UdZcu9' and contains(text(),'Delete')]");



    private WebElement getCaseName(String name){
        return findElement(By.xpath("//div[contains(text(),'"+name+"')]/parent::div//input"));
    }

    private WebElement deleteSuiteForNameBtn(String name){
        return findElement(By.xpath("//span[text()='"+name+"']//following::i[contains(@class,'fa-trash')]"));
    }


    public RepositoryPage verifyPageTitle() {
        Assert.assertTrue(getText(title).contains("repository"), "Title does not match as expected");
        return this;
    }

    public RepositoryPage clickImport() {
        click(ellipsisBtn);
        click(importDataBtn);
        return this;
    }

    public RepositoryPage clickExport() {
        click(ellipsisBtn);
        click(exportDataBtn);
        return this;
    }

    public RepositoryPage importFile(String filename){
        uploadFile(filename, this.chooseFile);
        click(importBtn);
        return this;
    }

    public RepositoryPage exportFile(){
        click(exportBtn);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public RepositoryPage verifyFlashMessage() {
        wait.until(ExpectedConditions.presenceOfElementLocated(flashMessage));
        Assert.assertTrue(getText(flashMessage).contains("were successfully imported!") , "Title does not match as expected");
        elementNotExist(flashMessage);
        return this;
    }

    public RepositoryPage verifySuiteTitle() {
        Assert.assertEquals(getText(suiteTitle),"Suites", "Title does not match as expected");
        return this;
    }

    public RepositoryPage verifyLastFileInDirectory() {
        File lastFile = getLastDownloadFileInDirectory();
        log.debug("I'm found file :: " + lastFile.getName());
        Assert.assertTrue(lastFile.exists());
        lastFile.deleteOnExit();
        log.debug("I'm deleted file :: " + lastFile.getName());
        return this;
    }

    public RepositoryPage createCase() {
        click(createNewCase);
        return this;
    }

    public RepositoryPage deleteAllCases() {
        clickWithJS(selectAllCases);
        click(deleteCaseBtn);
        enter(inputConfirm,"CONFIRM");
        click(confirmDeleteBtn);
        return this;
    }

    public RepositoryPage deleteCaseByName(String caseName) {
        clickWithJS(getCaseName(caseName));
        click(deleteCaseBtn);
        enter(inputConfirm,"CONFIRM");
        click(confirmDeleteBtn);
        return this;
    }

    public RepositoryPage createSuite() {
        click(createNewSuite);
        return this;
    }

    public RepositoryPage deleteSuiteForCount(int countDelete) {
        for(int i=1; i<=countDelete; i++) {
            retryingFindClick(deleteSuiteBtn);
            click(confirmDeleteBtn);
        }
        return this;
    }

    public RepositoryPage deleteSuiteByName(String name) {
        click(deleteSuiteForNameBtn(name));
        click(confirmDeleteBtn);
        return this;
    }

    public RepositoryPage verifyFlashMessageDelete() {
        wait.until(ExpectedConditions.presenceOfElementLocated(flashMessage));
        Assert.assertTrue(getText(flashMessage).contains("successfully deleted") , "Title does not match as expected");
        elementNotExist(flashMessage);
        return this;
    }

}
