package library_API;

import convert.File_Convert;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import payLoad.body;

import static io.restassured.RestAssured.given;

public class AddBook_DataPro {
    @Test(dataProvider = "BookDet")
    public void addBook(String isbn, String aisle) {

        RestAssured.baseURI = "http://216.10.245.166";
        Response response =
                given()
                        .body(body.addBook(isbn, aisle))
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

    @DataProvider(name="BookDet")
    public Object[][] getData() {

        return new Object[][] {
                {"qazwsx","9876"},
                {"edcrfv","5678"}
        };
    }
}
