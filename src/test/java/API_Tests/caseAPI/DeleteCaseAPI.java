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

public class DeleteCaseAPI extends ResponseHelper {
    String uri = "/case/";

    @BeforeTest(description = "Create test data")
    public void createProject() {
        Project newProject= new Project.ProjectBuilder()
                .withTitle("ForDelete")
                .withCode("DD")
                .withDescription("Description for new test project")
                .withAccess("all").create();

     super.post(converter.toJson(newProject),"/project",200);

        for (int i=1; i<=4; i++) {
            CaseFaker caseFaker = new CaseFaker();
            Case newCase = caseFaker.getCase();

            Response response = super.post(converterForJavaFaker.toJson(newCase), uri + "/DD", 200);
            Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"true");
        }
    }

    @Test(description = "Deleting an existing object",priority = 1)
    public void deleteCase1() {
        Response response = super.delete(uri+"DD","/3",200);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"true");
    }

    @Test(description = "Deleting an already deleted object", priority = 2)
    public void deleteCase2() {
        Response response = super.delete(uri+"DD","/3",404);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorMessage"),"Test case not found");
    }

    @Test(description = "Deleting by non-existent 'Code'", priority = 3)
    public void deleteCase3() {
        Response response = super.delete(uri+"DD","/3455",404);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorMessage"),"Test case not found");
    }

    @Test(description = "Deleting by invalid 'Code' (Code less than one character)",priority = 4)
    public void deleteProject4() {
        Response response = super.delete(uri+"DD","/qws",400);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"false");
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorMessage"),"The given route params are invalid");
    }

    @AfterTest(description = "Deleting test data after test",alwaysRun = true)
    public void deleteProject() {
        Response response = super.delete("/project","/DD",200);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"true");
    }

}
