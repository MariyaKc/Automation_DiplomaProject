package API_Tests.caseAPI;

import APIHelper.ResponseHelper;
import entity.Case;
import entity.Project;
import io.restassured.response.Response;
import javaFaker.CaseFaker;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class GetCaseAPI extends ResponseHelper {

    String uri = "/case/";

    @BeforeTest(description = "Create test data")
    public void createProject() {
        Project newProject= new Project.ProjectBuilder()
                .withTitle("Automation")
                .withCode("PR")
                .withDescription("Description for new test project")
                .withAccess("all").create();

        Response create = super.post(converter.toJson(newProject),"/project",200);

        for (int i=1; i<=10; i++) {
            CaseFaker caseFaker = new CaseFaker();
            Case newCase = caseFaker.getCase();

            Response response = super.post(converterForJavaFaker.toJson(newCase), uri + "/PR", 200);
            Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"true");

        }
    }

    @Test(description = "Test get all cases", priority = 1)
    public void getCase() {
        Response response = super.get( uri+"/PR","", 200);
        Assert.assertEquals(response.then().extract().response().statusCode(), 200);
        response.then().assertThat().body(matchesJsonSchema(getJsonData("cases_sch")));

    }

    @Test(description = "Requesting data by valid code, verifying the return of correct data",priority = 2)
    public void getCase2() {
        Response response = super.get( uri+"/PR","/1", 200);
        Assert.assertEquals(response.then().extract().response().statusCode(), 200);
        response.then().assertThat().body(matchesJsonSchema(getJsonData("case_sch")));
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"true");

    }

    @Test(description = "Request data by non-existent code, but in a valid format",priority = 3)
    public void getCase3() {
        Response response = super.get( uri+"/PR","/1234", 404);
        Assert.assertEquals(response.then().extract().response().statusCode(), 404);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"false");
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorMessage"),"Test case not found");
    }

    @Test(description = "Requesting data by invalid code", priority = 4)
    public void getCase4() {
        Response response = super.get( uri+"/PR","/QW", 400);
        Assert.assertEquals(response.then().extract().response().statusCode(), 400);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"false");
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorFields.id"),"[The id must be an integer.]");
    }

    @Test(description = "Getting a list with a limit on the number of entries",priority = 5)
    public void getCase5() {
        Response response = super.get( uri+"/PR","?limit=3", 200);

        List<Project> codes = response.getBody().path("result.entities");
        Assert.assertTrue(codes.size() == 3);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"true");
    }

    @Test(description = "Getting a list starting from the specified number",priority = 6)
    public void getCase6() {
        Response response = super.get( uri+"/PR","?offset=4", 200);

        List<Integer> codes = response.getBody().path("result.entities.id");
        Integer id = codes.get(0);

        Assert.assertEquals(id, 5);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"true");
    }

    @Test(description = "Test with a negative offset value",priority = 7)
    public void getCase7() {
        Response response = super.get( uri+"/PR","?offset=-4", 400);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"false");
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorFields.error"),"[The offset must be at least 0.]");
    }

    @Test(description = "Test with non-existent offset value",priority = 8)
    public void getCase8() {
        Response response = super.get( uri+"/PR","?offset=111", 200);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"true");
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("result.entities"),"[]");
    }

    @AfterTest(description = "Deleting test data after test",alwaysRun = true)
    public void deleteProject() {
        Response response = super.delete("/project","/PR",200);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"true");
    }
}
