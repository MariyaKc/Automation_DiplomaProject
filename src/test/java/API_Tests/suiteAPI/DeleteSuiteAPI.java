package API_Tests.suiteAPI;

import APIHelper.ResponseHelper;
import entity.Case;
import entity.Project;
import entity.Suite;
import io.restassured.response.Response;
import javaFaker.CaseFaker;
import javaFaker.SuiteFaker;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DeleteSuiteAPI extends ResponseHelper {

    String uri = "/suite/";

    @BeforeTest(description = "Create tests data")
    public void createProject() {

        Project newProject = new Project.ProjectBuilder()
                .withTitle("Suite Delete Project")
                .withCode("SD")
                .withDescription("Description for new test project")
                .withAccess("all").create();

        super.post(converter.toJson(newProject), "/project", 200);

        for (int i = 1; i <= 4; i++) {
            SuiteFaker suiteFaker = new SuiteFaker();
            Suite suite = suiteFaker.getSuite();

            Response response = super.post(converterForJavaFaker.toJson(suite), uri + "/SD", 200);
            Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"), "true");
        }
    }

    @Test(description = "Deleting an existing object",priority = 1)
    public void deleteSuite1() {
        Response response = super.delete(uri+"SD","/2",200);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"true");
    }

    @Test(description = "Deleting an already deleted object",priority = 2)
    public void deleteSuite2() {
        Response response = super.delete(uri+"SD","/2",404);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorMessage"),"Suite not found");
    }

    @Test(description = "Deleting by non-existent 'Code'",priority = 3)
    public void deleteSuite3() {
        Response response = super.delete(uri+"SD","/3455",404);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorMessage"),"Suite not found");
    }

    @Test(description = "Deleting by invalid 'Code' (Code less than one character)",priority = 4)
    public void deleteSuite4() {
        Response response = super.delete(uri+"SD","/qws",400);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"false");
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorMessage"),"The given route params are invalid");
    }

    @AfterTest(description = "Deleting test data after test",alwaysRun = true)
    public void deleteProject() {
        Response response = super.delete("/project","/SD",200);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"true");
    }

}
