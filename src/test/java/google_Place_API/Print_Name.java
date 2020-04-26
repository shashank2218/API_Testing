package google_Place_API;

import convert.File_Convert;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Print_Name {

    @Test
    public void getAllNames() {
        Response response =
        given()
                .param("location","-33.8670522,151.1957362")
                .param("radius", "500")
                .param("key", "AIzaSyANZej_HDeJWVah3FkoG4SX1oEKbrsKI4A")
        .when()
                .get("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
        .then()
                .statusCode(200)
        .extract().response();

        JsonPath jsonPath = File_Convert.rawToJson(response);

        int count = jsonPath.get("results.size()");

        for (int i=0; i<count; i++) {
            String name = jsonPath.get("results["+i+"].name");
            System.out.println(name);
        }
        System.out.println(count);
    }
}
