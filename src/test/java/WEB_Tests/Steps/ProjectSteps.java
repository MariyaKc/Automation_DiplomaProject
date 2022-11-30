package WEB_Tests.Steps;

import entity.Project;
import io.qameta.allure.Step;
import javaFaker.ProjectFaker;
import pageObjects.baseObjects.BasePage;
import pageObjects.qaseSelenium.LoginPage;
import pageObjects.qaseSelenium.ProjectPage;

public class ProjectSteps extends BasePage {

    @Step("Create project ::{title}")
    public ProjectSteps createProject (String title, String code){
        Project project = new Project.ProjectBuilder()
                .withTitle(title)
                .withCode(code)
                .withDescription("Description for new test project")
                .withAccess("public").create();

        new ProjectPage().createNewProject().enterData(project).clickCreate();
        return this;
    }

    @Step("Create project")
    public ProjectSteps createProjectWithJavaFaker (Project project){
        new ProjectPage().createNewProject().enterData(project).clickCreate();
        return this;
    }


}
