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

public class PostSuiteAPI extends ResponseHelper {
    String uri = "/suite";

    @BeforeTest(description = "Create tests data")
    public void createProject() {
        Project newProject= new Project.ProjectBuilder()
                .withTitle("Suite Project")
                .withCode("SP")
                .withDescription("Description for new test project")
                .withAccess("all").create();

        super.post(converter.toJson(newProject),"/project",200);
    }


    @Test(description = "Test with required fields with valid data",priority = 1)
    public void createSuite1() {
        SuiteFaker suiteFaker = new SuiteFaker();
        Suite suite = suiteFaker.getSuite();

        Response response = super.post(converter.toJson(suite),uri +"/SP",200);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"true");
    }

    @Test(description = "Test with empty one of required field 'Title'",priority = 2)
    public void createCase2() {
        Suite suite = new Suite.SuiteBuilder()
                .withPreconditions("precondition")
                .withDescription("Description").create();

        Response response = super.post(converter.toJson(suite),uri +"/SP",400);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"false");
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorFields.error"),"[The title field is required.]");
    }

    @Test(description = "Test with empty all of fields",priority = 3)
    public void createSuite3() {
        Suite suite = new Suite.SuiteBuilder()
                .withSuiteName("")
                .withPreconditions("")
                .withDescription("").create();

        Response response = super.post(converter.toJson(suite),uri +"/SP",400);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"false");
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorMessage"),"Data is invalid.");
    }

    @Test(description = "Test with empty body",priority = 4)
    public void createSuite4() {
        Suite suite = new Suite.SuiteBuilder().create();

        Response response = super.post(converter.toJson(suite),uri +"/SP",400);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"false");
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorFields.error"),"[The title field is required.]");
    }

    @AfterTest(description = "Deleting test data after test",alwaysRun = true)
    public void deleteProject() {
        Response response = super.delete("/project","/SP",200);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"true");
    }

}
