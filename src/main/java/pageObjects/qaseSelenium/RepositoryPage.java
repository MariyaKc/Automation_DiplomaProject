package pageObjects.qaseSelenium;

import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
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
    private final By chooseFile = By.xpath("//input[@type='file']");
    private final By importBtn = By.xpath("//*[@id='modals']//button[contains(@class,'tscvgR')]");
    private final By exportBtn = By.xpath("//button[contains(@class,'btn-primary')]");
    private final By flashMessage = By.className("OL6rtE");
    private final By suiteTitle = By.className("FhcTGD");

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


}
