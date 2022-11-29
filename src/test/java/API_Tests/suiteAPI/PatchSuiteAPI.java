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

public class PatchSuiteAPI extends ResponseHelper {
    String uri = "/suite/";

    @BeforeTest(description = "Create tests data")
    public void createProject() {

        Project newProject = new Project.ProjectBuilder()
                .withTitle("Suite Patch Project")
                .withCode("PP")
                .withDescription("Description for new test project")
                .withAccess("all").create();

        super.post(converter.toJson(newProject), "/project", 200);

        for (int i = 1; i <= 5; i++) {
            SuiteFaker suiteFaker = new SuiteFaker();
            Suite suite = suiteFaker.getSuite();

            Response response = super.post(converterForJavaFaker.toJson(suite), uri + "/PP", 200);
            Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"), "true");
        }
    }

    @Test(description = "Test update with correct data",priority = 1)
    public void patchSuite1() {
        SuiteFaker suiteFaker = new SuiteFaker();
        Suite suite = suiteFaker.getSuite();

        Response response = super.patch(converterForJavaFaker.toJson(suite), uri+"PP", "/2",200);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"true");
        Assert.assertEquals(response.then().extract().response().jsonPath().getInt("result.id"),2);
    }

    @Test(description = "Updating by a non-existent code",priority = 2)
    public void patchSuite2() {
        SuiteFaker suiteFaker = new SuiteFaker();
        Suite suite = suiteFaker.getSuite();

        Response response = super.patch(converterForJavaFaker.toJson(suite), uri+"PP", "/1234",404);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"false");
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorMessage"),"Test suite not found");
    }

    @Test(description = "Updating by invalid code",priority = 3)
    public void patchSuite3() {
        SuiteFaker suiteFaker = new SuiteFaker();
        Suite suite = suiteFaker.getSuite();

        Response response = super.patch(converterForJavaFaker.toJson(suite), uri+"PP", "/qwee",400);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"false");
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorFields.id"),"[The id must be an integer.]");
    }

    @Test(description = "Update with incorrect data",priority = 4)
    public void patchSuite4() {
        Suite suite = new Suite.SuiteBuilder()
                .withSuiteName(" ").create();

        Response response = super.patch(converter.toJson(suite), uri+"PP", "/3",400);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorMessage"),"Data is invalid.");
    }

    @Test(description = "Partial update",priority = 5)
    public void patchSuite5() {
        Suite suite = new Suite.SuiteBuilder()
                .withSuiteName("Name")
                .withPreconditions("precondition").create();
        Response response = super.patch(converter.toJson(suite), uri+"PP", "/4",200);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"true");
    }

    @AfterTest(description = "Deleting test data after test",alwaysRun = true)
    public void deleteProject() {
        Response response = super.delete("/project","/PP",200);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"true");
    }
}
