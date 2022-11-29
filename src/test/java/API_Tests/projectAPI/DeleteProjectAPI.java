package API_Tests.projectAPI;

import APIHelper.ResponseHelper;
import entity.Project;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteProjectAPI extends ResponseHelper {
    String uri = "/project";

    @Test(description = "Deleting an existing object", priority = 1)
    public void deleteProject() {
        Project newProject= new Project.ProjectBuilder()
                .withTitle("WEB Site")
                .withCode("WEB").create();
        Response create = super.post(converter.toJson(newProject),uri,200);

        Response response = super.delete(uri,"/WEB",200);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"true");
    }

    @Test(description = "Deleting an already deleted object", priority = 3)
    public void deleteProject2() {
        Response response = super.delete(uri,"/WEB",404);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorMessage"),"Project is not found.");
    }

    @Test(description = "Deleting by non-existent 'Code'", priority = 2)
    public void deleteProject3() {
        Response response = super.delete(uri,"/QA",404);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorMessage"),"Project is not found.");
    }

    @Test(description = "Deleting by invalid 'Code' (Code less than one character)", priority = 4)
    public void deleteProject4() {
        Response response = super.delete(uri,"/Q",404);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("status"),"false");
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorMessage"),"Project code is invalid");
    }
}
