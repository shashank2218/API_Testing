package google_Place_API;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GET {

    @Test
    public void getGooglePlace() {

        given()
                .param("location","-33.8670522,151.1957362")
                .param("radius","500")
                .param("key","AIzaSyANZej_HDeJWVah3FkoG4SX1oEKbrsKI4A")
        .when()
                .get("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
        .then()
                .statusCode(200)
                .and()
                .body("results[0].name",equalTo("Sydney"))
                .and()
                .header("Server","scaffolding on HTTPServer2")
                .log().all();
    }
}
