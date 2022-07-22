package activities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Activity2 {
    // Set base URL
     String baseURL = "https://petstore.swagger.io/v2/user";

    @Test(priority=1)
    public void postRequest() throws IOException {
        FileInputStream inputJSON = new FileInputStream("src/test/java/activities/userinfo.json");
        String reqBody = new String(inputJSON.readAllBytes());
        Response response =
                given().contentType(ContentType.JSON)
                        .body(reqBody)
                        .when().post(baseURL);

        inputJSON.close();
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("5935"));
    }

    @Test(priority=2)
    public void getRequest() {
        File outputJSON = new File("src/test/java/activities/userGETResponse.json");
        Response response =
                given().contentType(ContentType.JSON)
                        .pathParam("username", "pmaalipa")
                        .when().get(baseURL + "/{username}");
        String resBody = response.getBody().asPrettyString();

        try {
            outputJSON.createNewFile();
            FileWriter writer = new FileWriter(outputJSON.getPath());
            writer.write(resBody);
            writer.close();
        } catch (IOException exp) {
            exp.printStackTrace();
        }

        response.then().body("id", equalTo(5935));
        response.then().body("username", equalTo("pmaalipa"));
        response.then().body("firstName", equalTo("Justin"));
        response.then().body("lastName", equalTo("Case"));
        response.then().body("email", equalTo("justincase@mail.com"));
        response.then().body("password", equalTo("password123"));
        response.then().body("phone", equalTo("9812763450"));
    }

    @Test(priority=3)
    public void deleteRequest() throws IOException {
        Response response =
                given().contentType(ContentType.JSON)
                        .pathParam("username", "justinc")
                        .when().delete(baseURL + "/{username}");
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("pmaalipa"));
    }
}
