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

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class PostCaseAPI extends ResponseHelper {
    String uri = "/case/";

    @BeforeTest(description = "Create test data")
    public void createProject() {
        Project newProject = new Project.ProjectBuilder()
                .withTitle("Automation Project")
                .withCode("AP")
                .withDescription("Description for new test project")
                .withAccess("all").create();

        Response create = super.post(converter.toJson(newProject), "/project", 200);
    }

    @Test(description = "Test with all fields with valid data", priority = 1)
    public void createCaseWithRequiredFields() {
        CaseFaker caseFaker = new CaseFaker();
        Case newCase = caseFaker.getCase();

        Response response = super.post(converterForJavaFaker.toJson(newCase), uri + "/AP", 200);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"), "true");
    }

    @Test(description = "Test with required fields with valid data", priority = 2)
    public void createCase() {
        Case aCase = new Case.CaseBuilder()
                .withTitle("AutomationQA")
                .create();

        Response response = super.post(converter.toJson(aCase), uri + "/AP", 200);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"), "true");
    }

    @Test(description = "Test with empty one of required field 'Title'", priority = 3)
    public void createCase2() {
        Case aCase = new Case.CaseBuilder()
                .withType("Smoke")
                .withIsFlaky("No")
                .withBehavior("Negative")
                .withAutomationStatus("Automated")
                .create();

        Response response = super.post(converter.toJson(aCase), uri + "/AP", 422);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("message"), "The title field is required.");

    }

    @Test(description = "Test with empty all of fields", priority = 4)
    public void createCase3() {
        Case aCase = new Case.CaseBuilder()
                .withTitle("")
                .withStatus("")
                .withDescription("")
                .withSeverity("")
                .withPriority("")
                .withType("")
                .withLayer("")
                .withIsFlaky("")
                .withBehavior("")
                .withAutomationStatus("")
                .withPreconditions("")
                .withPostconditions("")
                .create();

        Response response = super.post(converter.toJson(aCase), uri + "/AP", 422);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("message"), "The title field is required.");
    }

    @Test(description = "Test with empty body", priority = 5)
    public void createCase4() {
        Case aCase = new Case.CaseBuilder().create();

        Response response = super.post(converter.toJson(aCase), uri + "/AP", 422);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("message"), "The title field is required.");
    }


    @AfterTest(description = "Deleting test data after test", alwaysRun = true)
    public void deleteProject() {
        Response response = super.delete("/project", "/AP", 200);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"), "true");
    }
}

