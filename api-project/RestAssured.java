package liveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.testng.TestRunner.PriorityWeight.priority;

public class RestAssured {

    String sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCx9A2L57LFUl4jRkth1CNT3yBdxz3TCtwpvU0Rss9ckF6Dj6nzuoQeVXpf81eB16E/1mhvPOWztG0v48flcmcsiLmaeh1O75CuCcukTcUG5IYlSwKrJVgZtnMztNPBZyPA23CUSVoD9XliaMoYa9/zXOoHGbsm0PXJIpWVLONa7JrG3daoV3JStDJeTg7gOrxryWvv4vUfb7g/qIw6qYE5Z+LOUpzuvAjLzu84ECtB8B4j9RPiJ8YxmwRUJQLxK2A0jUAuCNed3YyslCP/y9jrFQPO5l8HPtET3AGnx9lJpItNtKlJEwqe+driu6xuC4VKCZkM/1Q6GS3jOjoGLiHv";
    int id;
    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;

    @BeforeClass
    public void setUp(){
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://api.github.com")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "token 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")
                .build();
    }

    @Test(priority=1)
    public void addKey(){
        Map<String, String> map = new HashMap<>();
         map.put("title", "TestAPIKey")   ;
         map.put("key", sshKey);

        String resourcePath = "/user/keys";
        Response response = given().spec(requestSpec)
                .body(map)
                .when().post("/user/keys");

        System.out.println(response.getBody().asString());
        id = response.then().extract().path("id");
        System.out.println(id);
        response.then().statusCode(201);
        //response.then().body("code", equalTo(201));

    }

    @Test(priority=2)
    public void getKey(){
        Response response = given().spec(requestSpec)
                .pathParam("keyId", id)
                .when().get(" /user/keys/{keyId}");

        System.out.println("inside the get pet" + response.getBody().asString());
        //response.then().statusCode(200);
        Reporter.log("response of the get operation" +response.getBody().asString());
    }

    @Test(priority=3)
    public void keyDelete(){
        Response response = given().spec(requestSpec)
                .pathParam("keyId", id)
                .when().delete(" /user/keys/{keyId}");

        System.out.println("inside the get pet" + response.getBody().asString());
        response.then().statusCode(204);
    }

}
