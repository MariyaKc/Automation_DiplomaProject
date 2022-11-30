package cucumberSteps;

import entity.Project;
import io.cucumber.java.en.Then;
import javaFaker.ProjectFaker;
import pageObjects.baseObjects.BaseTest;
import pageObjects.qaseSelenium.ProjectPage;
import pageObjects.qaseSelenium.navigation.NavigationTab;

public class ProjectSteps extends BaseTest {

    protected static Project newProject;

    @Then("I create project")
    public void createProject() {
        ProjectFaker projectFaker = new ProjectFaker();
        newProject = projectFaker.getProject();
        get(ProjectPage.class).createNewProject().enterData(newProject).clickCreate();
    }

    @Then("I delete Project")
    public void deleteProject(){
        get(NavigationTab.class).clickNavigationItem("Projects");
        get(ProjectPage.class).deleteForName(newProject.getTitle());
    }
}

