package basicRest;

import convert.File_Convert;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.annotations.Test;
import payLoad.body;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class EndToEndPlaceAPI {

    @Test
    public static void placeAPI() {

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        Response response = given()
                .queryParam("key", "qaclick123")
                .contentType("application/json")
                .body(body.AddPlace())
        .when()
                .post("maps/api/place/add/json")
        .then()
                .statusCode(200)
                .body("scope", equalTo("APP"))
                .log().all()
        .extract().response();

        JsonPath jsonPath = File_Convert.rawToJson(response);
        String placeId = jsonPath.getString("place_id");
        System.out.println(placeId);

        //Update Address
        String newAddress = "Hanover Street";
        given()
                .queryParam("key", "qaclick123")
                .contentType("application/json")
                .body("{\r\n" +
                        "\"place_id\":\""+placeId+"\",\r\n" +
                        "\"address\":\""+newAddress+"\",\r\n" +
                        "\"key\":\"qaclick123\"\r\n" +
                        "}")
        .when()
                .put("maps/api/place/update/json")
        .then()
                .statusCode(200)
                .body("msg", equalTo("Address successfully updated"))
                .log().all();

        //Get API
        given()
                .queryParam("key", "qaclick123")
                .queryParam("place_id", placeId)
        .when()
                .get("maps/api/place/get/json")
        .then()
                .statusCode(200)
                .body("address", equalTo(newAddress))
                .log().all();


    }
}
