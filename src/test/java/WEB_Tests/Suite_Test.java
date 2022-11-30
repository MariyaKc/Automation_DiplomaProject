package WEB_Tests;
import WEB_Tests.Steps.LoginSteps;
import WEB_Tests.Steps.ProjectSteps;
import entity.Suite;
import javaFaker.SuiteFaker;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.baseObjects.BaseTest;
import pageObjects.qaseSelenium.ProjectPage;
import pageObjects.qaseSelenium.RepositoryPage;
import pageObjects.qaseSelenium.SuitePage;
import pageObjects.qaseSelenium.navigation.NavigationTab;


public class Suite_Test extends BaseTest {

    @BeforeTest
    public void preconditions() {
        get(LoginSteps.class).login();
        get(ProjectSteps.class).createProject("ProjectWithSuite","SI");
    }

    @Test(description = "Test for create 3 new suites with all fields (using Java Faker) and delete suites by count")
    public void createSuites() {
        for (int i = 1; i<=3; i++) {
        get(RepositoryPage.class).createSuite();

            SuiteFaker suiteFaker = new SuiteFaker();
            Suite suite = suiteFaker.getSuite();
            new SuitePage().enterData(suite).createSuite().verifySuiteIsCreate();
        }
        get(RepositoryPage.class).deleteSuiteForCount(3).verifyFlashMessageDelete();
    }

    @Test(description = "Test for create new case with with only required fields and delete suite by name ")
    public void createAndDeleteSuite() {
        Suite suite = new Suite.SuiteBuilder()
                .withSuiteName("Suite")
                .withDescription("")
                .withPreconditions("")
                .create();
        get(RepositoryPage.class).createSuite();
        new SuitePage().enterData(suite).createSuite().verifySuiteIsCreate();

        get(RepositoryPage.class).deleteSuiteByName("Suite").verifyFlashMessageDelete();
    }

    @AfterTest
    public void post(){
        get(NavigationTab.class).clickNavigationItem("Projects");
        get(ProjectPage.class).deleteForName("ProjectWithSuite");

    }
}
