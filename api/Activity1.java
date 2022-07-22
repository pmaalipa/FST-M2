package activities;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Activity1 {
    // Set base URL
    final static String baseURL = "https://petstore.swagger.io/v2/pet";

    @Test
    public void postRequest() {
        String reqBody = "{"
                + "\"id\": 77232,"
                + "\"name\": \"Riley\","
                + " \"status\": \"alive\""
                + "}";

        Response response =
                given().contentType(ContentType.JSON)
                        .body(reqBody)
                        .when().post(baseURL);

        response.then().body("id", equalTo(77232));
        response.then().body("name", equalTo("Riley"));
        response.then().body("status", equalTo("alive"));
    }

    @Test
    public void getRequest() {
        Response response =
                given().contentType(ContentType.JSON)
                        .when().pathParam("petId", "77232")
                        .get(baseURL + "/{petId}");

        // Assertion
        response.then().body("id", equalTo(77232));
        response.then().body("name", equalTo("Riley"));
        response.then().body("status", equalTo("alive"));
    }

    @Test
    public void deletePet() {
        Response response =
                given().contentType(ContentType.JSON)
                        .when().pathParam("petId", "77232")
                        .delete(baseURL + "/{petId}");
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("77232"));
    }
}
