package APIHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;
public class ResponseHelper {
    private static final String baseUrl = "https://api.qase.io/v1";
    private static final String tokenValue = "ab5c7b6100131c1a6b4483f279ac202ef865ab27";
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
                    .setContentType(ContentType.JSON)
                    .addHeader("Token", tokenValue)
                    .log(LogDetail.METHOD)
                    .log(LogDetail.BODY)
                    .build();

    protected Gson converter = new Gson();
    protected Gson converterForJavaFaker =
            new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                    .create();

    protected Response post(String body, String uri, int statusCode){
        return
        given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post(baseUrl + uri)
                .then()
                .log().all()
                .statusCode(statusCode).extract().response();
    }

    protected Response get(String uri, String code, int statusCode) {
        return
                given()
                        .spec(requestSpec)
                        .when()
                        .get(baseUrl + uri + code)
                        .then()
                        .statusCode(statusCode)
                        .log().all()
                        .extract().response();
    }

    protected Response patch(String body, String uri, String code, int statusCode) {
        return
                given()
                        .spec(requestSpec)
                        .body(body)
                        .when()
                        .patch(baseUrl + uri+ code)
                        .then()
                        .log().all()
                        .statusCode(statusCode)
                        .extract().response();
    }


    protected Response delete(String uri, String code, int statusCode){
        return
                given()
                        .spec(requestSpec)
                        .when()
                        .delete(baseUrl + uri + code)
                        .then()
                        .log().all()
                        .statusCode(statusCode)
                        .extract().response();
    }

    public String getJsonData(String filename) {
        try {
            return new String(Files.readAllBytes(Paths.get("files/jsonschema/" + filename + ".json")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
