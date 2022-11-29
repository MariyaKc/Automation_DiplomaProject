package API_Tests.suiteAPI;

import APIHelper.ResponseHelper;
import entity.Project;
import entity.Suite;
import io.restassured.response.Response;
import javaFaker.SuiteFaker;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class GetSuiteAPI extends ResponseHelper {
    String uri = "/suite/";

    @BeforeTest(description = "Create tests data")
    public void createProject() {

        Project newProject = new Project.ProjectBuilder()
                .withTitle("Suite Get Project")
                .withCode("GD")
                .withDescription("Description for new test project")
                .withAccess("all").create();

        super.post(converter.toJson(newProject), "/project", 200);

        for (int i = 1; i <= 10; i++) {
            SuiteFaker suiteFaker = new SuiteFaker();
            Suite suite = suiteFaker.getSuite();

            Response response = super.post(converterForJavaFaker.toJson(suite), uri + "/GD", 200);
            Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"), "true");
        }
    }

    @Test(description = "Test get all suites",priority = 1)
    public void getSuite1() {
        Response response = super.get( uri+"/GD","", 200);
        response.then().assertThat().body(matchesJsonSchema(getJsonData("suites_sch")));

    }

    @Test(description = "Requesting data by valid code, verifying the return of correct data",priority = 2)
    public void getSuite2() {
        Response response = super.get( uri+"/GD","/3", 200);
        response.then().assertThat().body(matchesJsonSchema(getJsonData("suite_sch")));
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"true");

    }

    @Test(description = "Request data by non-existent code, but in a valid format",priority = 3)
    public void getSuite3() {
        Response response = super.get( uri+"/GD","/1234", 404);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"false");
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorMessage"),"Suite not found");
    }

    @Test(description = "Requesting data by invalid code",priority = 4)
    public void getSuite4() {
        Response response = super.get( uri+"/GD","/QW", 400);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"false");
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorFields.id"),"[The id must be an integer.]");
    }

    @Test(description = "Getting a list with a limit on the number of entries",priority = 5)
    public void getSuite5() {
        Response response = super.get( uri+"/GD","?limit=2", 200);

        List<Project> codes = response.getBody().path("result.entities");
        Assert.assertTrue(codes.size() == 2);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"true");
    }

    @Test(description = "Getting a list starting from the specified number",priority = 6)
    public void getSuite6() {
        Response response = super.get( uri+"/GD","?offset=6", 200);

        List<Integer> codes = response.getBody().path("result.entities.id");
        Integer id = codes.get(0);

        Assert.assertEquals(id, 7);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"true");
    }

    @Test(description = "Test with a negative offset value",priority = 7)
    public void getSuite7() {
        Response response = super.get( uri+"/GD","?offset=-6", 400);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"false");
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorFields.error"),"[The offset must be at least 0.]");
    }

    @Test(description = "Test with non-existent offset value",priority = 8)
    public void getSuite8() {
        Response response = super.get( uri+"/GD","?offset=113", 200);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"true");
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("result.entities"),"[]");
    }

    @AfterTest(description = "Deleting test data after test",alwaysRun = true)
    public void deleteProject() {
        Response response = super.delete("/project","/GD",200);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"true");
    }
}
