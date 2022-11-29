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

public class PatchCaseAPI extends ResponseHelper {
    String uri = "/case/";

    @BeforeTest(description = "Create test data")
    public void createProject() {
        Project newProject= new Project.ProjectBuilder()
                .withTitle("Put Case Project")
                .withCode("PC")
                .withDescription("Description for new test project")
                .withAccess("all").create();
        super.post(converter.toJson(newProject),"/project",200);

        for (int i=1; i<=10; i++) {
            CaseFaker caseFaker = new CaseFaker();
            Case newCase = caseFaker.getCase();

            Response response = super.post(converterForJavaFaker.toJson(newCase), uri + "/PC", 200);
            Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"true");
        }
    }

    @Test(description = "Test update with correct data",priority = 1)
    public void patchCase1() {
        CaseFaker caseFaker = new CaseFaker();
        Case putCase = caseFaker.getCase();

        Response response = super.patch(converterForJavaFaker.toJson(putCase), uri+"PC", "/1",200);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"true");
        Assert.assertEquals(response.then().extract().response().jsonPath().getInt("result.id"),1);
    }

    @Test(description = "Updating by a non-existent code",priority = 2)
    public void patchCase2() {
        CaseFaker caseFaker = new CaseFaker();
        Case putCase = caseFaker.getCase();

        Response response = super.patch(converterForJavaFaker.toJson(putCase), uri+"PC", "/1234",404);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"false");
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorMessage"),"Test case is not found.");
    }

    @Test(description = "Updating by invalid code",priority = 3)
    public void patchCase3() {
        CaseFaker caseFaker = new CaseFaker();
        Case putCase = caseFaker.getCase();

        Response response = super.patch(converterForJavaFaker.toJson(putCase), uri+"PC", "/qwee",400);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"false");
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorFields.id"),"[The id must be an integer.]");
    }

    @Test(description = "Update with incorrect data",priority = 4)
    public void putCase4() {
            Case aCase = new Case.CaseBuilder()
                    .withTitle("  ").create();

        Response response = super.patch(converter.toJson(aCase), uri+"PC", "/2",422);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("message"),"The title must be a string.");
    }

    @Test(description = "Partial update")
    public void patchCase5() {
        Case aCase = new Case.CaseBuilder()
                .withType("Smoke")
                .withIsFlaky("No")
                .withBehavior("Negative")
                .withAutomationStatus("Automated").create();
        Response response = super.patch(converter.toJson(aCase), uri+"PC", "/4",200);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"true");
    }

    @AfterTest(description = "Deleting test data after test",alwaysRun = true)
    public void deleteProject() {
        Response response = super.delete("/project","/PC",200);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"true");
    }

}
