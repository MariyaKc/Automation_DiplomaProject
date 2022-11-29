package API_Tests.projectAPI;

import APIHelper.ResponseHelper;
import entity.Project;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;


public class PostProjectAPI extends ResponseHelper {
    String uri = "/project";

    @Test(description = "Test with all fields are filled with valid data",priority = 1)
    public void createProjectWithAllFields() {
        Project newProject= new Project.ProjectBuilder()
                .withTitle("Test Project")
                .withCode("TT")
                .withDescription("Description for new test project")
                .withAccess("all").create();

        Response response = super.post(converter.toJson(newProject),uri,200);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("result.code"), newProject.getCode());
        Response delete = super.delete(uri,"/TT",200);
    }

    @Test(description = "Test with required fields with valid data",priority = 2)
    public void createProjectWithRequiredFields() {
        Project newProject= new Project.ProjectBuilder()
                .withTitle("WEB Site")
                .withCode("WEB").create();

        Response response = super.post(converter.toJson(newProject),uri,200);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("result.code"), newProject.getCode());
        Response delete = super.delete(uri,"/WEB",200);
    }

    @Test(description = "Test with empty one of required field 'Code'",priority = 3)
    public void createProjectWithEmptyRequiredField() {
        Project newProject= new Project.ProjectBuilder()
                .withTitle("WEB Site")
                .withCode("").create();

        Response response = super.post(converter.toJson(newProject),uri,400);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorFields.error"),"[Project code is required.]");
    }

    @Test(description = "Test with empty all of required fields 'Title' and 'Code'",priority = 4)
    public void createProjectWithEmptyRequiredFields() {
        Project newProject = new Project.ProjectBuilder()
                .withTitle("")
                .withCode("").create();

        Response response = super.post(converter.toJson(newProject),uri,400);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorFields.error"),"[Title is required., Project code is required.]");
    }

    @Test(description = "Test with empty body",priority = 5)
    public void createProjectWithEmptyBody() {
        Project empty = new Project.ProjectBuilder().create();

        Response response = super.post(converter.toJson(empty),uri,400);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorFields.error"),"[Title is required., Project code is required.]");
    }

    @Test(description = "Test with field 'Code' with invalid data",priority = 6)
    public void createProjectWithInvalidData() {
        Project newProject = new Project.ProjectBuilder()
                .withTitle("Project")
                .withCode("1").create();

        Response response = super.post(converter.toJson(newProject),uri,400);
        Assert.assertEquals(response.then().extract().response().jsonPath().getString("errorFields.error"),"[Project code must be at least 2 characters.]");
    }
}
