package jira_API;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import payLoad.body;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class Create_Issue {

    Properties properties = new Properties();
    @BeforeTest
    public void getData() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("src/main/java/prop/Base.properties");
        properties.load(fileInputStream);
    }

    @Test
    public void createIssue () {

        RestAssured.baseURI = properties.getProperty("JIRAHOST");
        SessionFilter sessionFilter = new SessionFilter();
        Response response =
                given()
                        .header("Content-Type", "application/json")
                        .body("{ \"username\": \"shashank2182\", \"password\": \"Nishu2218@\" }")
                        .filter(sessionFilter)
                .when()
                        .post("rest/auth/1/session")
                .then()
                        .statusCode(200)
                .extract()
                        .response();

        //Create issue
        given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(body.createIssue())
                .filter(sessionFilter)
        .when()
                .post("/rest/api/2/issue")
        .then()
                .statusCode(201)
                .log().all();
    }
}
