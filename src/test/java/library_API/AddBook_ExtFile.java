package library_API;

import convert.File_Convert;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class AddBook_ExtFile {

    @Test
    public void addBook() throws IOException {

        RestAssured.baseURI = "http://216.10.245.166";
        Response response =
                given()
                        .body(generateStringFromExt("src/main/java/extFile/BookDetails.json"))
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

    public static String generateStringFromExt (String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}
