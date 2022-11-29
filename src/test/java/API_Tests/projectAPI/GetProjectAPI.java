package API_Tests.projectAPI;

import APIHelper.ResponseHelper;
import entity.Project;
import io.restassured.response.Response;
import javaFaker.ProjectFaker;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;


public class GetProjectAPI extends ResponseHelper {
    String uri = "/project";

    @BeforeTest
    public void precondition() {
        Project project= new Project.ProjectBuilder()
                .withTitle("Project")
                .withCode("PR")
                .withDescription("Description for new test project")
                .withAccess("all").create();
        Response response = super.post(converter.toJson(project), "/project", 200);
        }


    @Test(description = "Test get all projects",priority = 1)
    public void getProject1() {
        Response response = super.get( uri,"", 200);
        response.then().assertThat().body(matchesJsonSchema(getJsonData("projects_sch")));

    }

    @Test(description = "Test get project by exist 'Code'", priority = 2)
    public void getProject2() {
        Response response = super.get( uri,"/PR", 200);
        response.then().assertThat().body(matchesJsonSchema(getJsonData("project_sch")));

    }

    @Test(description = "Requesting data by a non-existent 'Code', but in a valid format",priority = 3)
    public void getProject3() {
        Response response = super.get( uri,"/WEB", 404);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorMessage"),"Project is not found.");
    }

    @Test(description = "Requesting data by invalid 'Code'",priority = 4)
    public void getProject4() {
        Response response = super.get( uri,"/W", 404);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorMessage"),"Project code is invalid");
    }

    @AfterTest(alwaysRun = true)
    public void postcondition() {
        Response response = super.delete(uri,"/PR",200);
    }

}
