package WEB_Tests;

import WEB_Tests.Steps.LoginSteps;
import WEB_Tests.Steps.ProjectSteps;
import entity.Case;
import entity.Project;
import javaFaker.CaseFaker;
import javaFaker.ProjectFaker;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.baseObjects.BaseTest;
import pageObjects.qaseSelenium.CasePage;
import pageObjects.qaseSelenium.ProjectPage;
import pageObjects.qaseSelenium.RepositoryPage;
import pageObjects.qaseSelenium.navigation.NavigationTab;


public class Case_Test extends BaseTest {
    Project newProject;

    @BeforeTest
    public void preconditions() {
        get(LoginSteps.class).login();

        ProjectFaker projectFaker = new ProjectFaker();
        newProject = projectFaker.getProject();

        get(ProjectSteps.class).createProjectWithJavaFaker(newProject);
    }

    @Test(description = "Test for create 3 new cases with all fields (using Java Faker) and delete cases by count")
    public void createAndDeleteCases() {
         get(RepositoryPage.class).createCase();
        for (int i = 1; i<=3; i++) {
            CaseFaker caseFaker = new CaseFaker();
            Case newCase = caseFaker.getCase();
            get(CasePage.class).enterData(newCase).saveAndCreateNewCase().verifyCaseIsCreate();
        }
        get(CasePage.class).cancel();
        get(RepositoryPage.class).deleteAllCases().verifyFlashMessageDelete();
    }

    @Test(description = "Test for create new case with with only required fields and delete case by name ")
    public void createAndDeleteCase() {
        Case aCase = new Case.CaseBuilder()
                .withTitle("AutomationQA")
                .withStatus("")
                .withDescription("")
                .withSeverity("")
                .withPriority("")
                .withType("")
                .withLayer("")
                .withIsFlaky("")
                .withBehavior("")
                .withAutomationStatus("")
                .withPreconditions("")
                .withPostconditions("")
                .create();

        get(RepositoryPage.class).createCase();
        get(CasePage.class).enterData(aCase).saveNewCase().verifyCaseIsCreate();
        get(RepositoryPage.class).deleteCaseByName("AutomationQA").verifyFlashMessageDelete();
    }

    @AfterTest
    public void post(){
        get(NavigationTab.class).clickNavigationItem("Projects");
        get(ProjectPage.class).deleteForName(newProject.getTitle());

    }
}
