package WEB_Tests;

import WEB_Tests.Steps.LoginSteps;
import entity.Project;
import javaFaker.ProjectFaker;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.baseObjects.BaseTest;
import pageObjects.qaseSelenium.ProjectPage;
import pageObjects.qaseSelenium.navigation.NavigationTab;


public class Projects_Test extends BaseTest {

    @BeforeTest
    public void preconditions() {
        get(LoginSteps.class).login();
    }

    @Test(description = "Test for create new project with all fields and delete by name", priority = 1)
    public void createAndDeleteProjectsTest() {
            ProjectFaker projectFaker = new ProjectFaker();
            Project newProject = projectFaker.getProject();

            get(ProjectPage.class).createNewProject().enterData(newProject).clickCreate();
            get(NavigationTab.class).clickNavigationItem("Projects");
            get(ProjectPage.class).deleteForName(newProject.getTitle()).verifyThenProjectWasDeleted(newProject.getTitle()).createNewProject();
    }

    @Test(description = "Test for boundary values in filed 'Project code' when creating a new project", dataProvider = "value data", dependsOnMethods = "createAndDeleteProjectsTest")
    public void boundaryValuesTest(String value, String error) {
        Project project = new Project.ProjectBuilder()
                .withTitle("BoundaryValueProject")
                .withCode(value)
                .withDescription("Description for new test project")
                .withAccess("public").create();
        get(ProjectPage.class).enterData(project).clickCreate();
        if (value.length() < 2 | value.length() > 10) {
            get(ProjectPage.class)
                    .verifyFlashMessage()
                    .verifyProjectCodeError(error);
        } else {
            get(NavigationTab.class).clickNavigationItem("Projects");
            get(ProjectPage.class).verifyThenProjectCreate(project.getTitle()).deleteForName(project.getTitle()).verifyThenProjectWasDeleted("BoundaryValueProject").createNewProject();
        }
    }

    @DataProvider(name = "value data")
    public Object[][] data() {
        return new Object[][]{
                {"1","The code must be at least 2 characters."},
                {"12",""},
                {"123",""},
                {"123456789",""},
                {"1234567890","The code may not be greater than 10 characters."},
                {"12345678901","The code may not be greater than 10 characters."},
        };
    }
}
