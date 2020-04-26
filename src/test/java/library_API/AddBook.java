package library_API;

import convert.File_Convert;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import payLoad.body;

import static io.restassured.RestAssured.given;

public class AddBook {

    @Test
    public void addBook() {

        RestAssured.baseURI = "http://216.10.245.166";
        Response response =
        given()
                .body(body.addBook("MeinKampf", "2218"))
        .when()
                .post("Library/Addbook.php")
        .then()
                .statusCode(200)
        .extract()
                .response();

        JsonPath jsonPath = File_Convert.rawToJson(response);
        String id = jsonPath.get("ID");
        System.out.println(id);
    }
}
