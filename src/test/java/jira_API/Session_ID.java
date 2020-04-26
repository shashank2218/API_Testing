package jira_API;

import convert.File_Convert;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import payLoad.body;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class Session_ID {

    Properties properties = new Properties();
    @BeforeTest
    public void getData() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("src/main/java/prop/Base.properties");
        properties.load(fileInputStream);
    }

    @Test
    public void getSessionID() {

        RestAssured.baseURI = properties.getProperty("JIRAHOST");
        Response response =
        given()
                .header("Content-Type", "application/json")
                .body("{ \"username\": \"shashank2182\", \"password\": \"Nishu2218@\" }")
        .when()
                .post("rest/auth/1/session")
        .then()
                .statusCode(200)
        .extract()
                .response();

        JsonPath jsonPath = File_Convert.rawToJson(response);
        String sessionID = jsonPath.get("session.value");
        System.out.println(sessionID);

        Response res =
        given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID="+sessionID)
                .body(body.createIssue())
        .when()
                .post("/rest/api/2/issue")
        .then()
                .statusCode(201)
                .log().all()
        .extract().response();
    }
}
