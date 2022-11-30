package WEB_Tests;
import WEB_Tests.Steps.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.baseObjects.BaseTest;
import pageObjects.qaseSelenium.*;
import pageObjects.qaseSelenium.navigation.NavigationTab;


public class Repository_Test extends BaseTest {

    @BeforeTest
    public void preconditions() {
        get(LoginSteps.class).login();
    }

    @Test(description = "Test the import of a data file into the Project")
    public void importDataTest() {
        get(ProjectSteps.class).createProject("TestProject", "TP");
        get(RepositoryPage.class).verifyPageTitle().clickImport()
                .importFile("WEB.json").verifyFlashMessage().verifySuiteTitle();
    }

    @Test(description = "Test for  export of data from the Project", dependsOnMethods = "importDataTest")
    public void exportDataTest() {
        get(RepositoryPage.class).verifyPageTitle().clickExport().exportFile().verifyLastFileInDirectory();
    }

    @AfterClass
    public void post(){
        get(NavigationTab.class).clickNavigationItem("Projects");
        get(ProjectPage.class).deleteForName("TestProject").verifyThenProjectWasDeleted("TestProject");
    }
}
