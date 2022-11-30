package WEB_Tests;

import entity.Defect;
import entity.Project;
import javaFaker.DefectFaker;
import javaFaker.ProjectFaker;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.baseObjects.SelenideBaseTest;
import pageObjects.qaseSelenide.*;
import pageObjects.qaseSelenide.navigation.NavigationTab;

public class Defect_Test extends SelenideBaseTest {

    Project newProject;

    @BeforeTest
    public void precondition() {
        get(HomePage.class).clickLogin();
        get(LoginPage.class)
                .enterEmail(properties.getProperty("email"))
                .enterPassword(properties.getProperty("password"))
                .clickLogin();

        ProjectFaker projectFaker = new ProjectFaker();
        newProject = projectFaker.getProject();

        get(ProjectPage.class).createNewProject().enterData(newProject).createProject();
    }

    @Test(description = "Test for create 3 new defects and delete by name")
    public void createAndDeleteDefect() {
        for (int i=1; i<=3; i++) {
            DefectFaker defectFaker = new DefectFaker();
            Defect defect = defectFaker.getDefect();

            get(DefectPage.class).open(newProject.getCode())
                            .enterData(defect).clickCreate().verifyFlashMessage()
                            .delete(defect.getDefectTitle());
        }
    }
    @AfterClass
    public void post(){
        get(NavigationTab.class).clickNavigationItem("Projects");
        get(ProjectPage.class).deleteForName(newProject.getTitle());
    }
}
