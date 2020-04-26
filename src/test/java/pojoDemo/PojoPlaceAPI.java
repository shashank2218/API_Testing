package pojoDemo;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojo.Location;
import pojo.PlaceAPI;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PojoPlaceAPI {

    @Test
    public void serializePlaceAPI () {

        PlaceAPI placeAPI = new PlaceAPI();
        placeAPI.setAccuracy(50);
        placeAPI.setAddress("22B, Baker Street");
        placeAPI.setLanguage("English");
        placeAPI.setPhone_number("1234567890");
        placeAPI.setName("Daily Eats");
        placeAPI.setWebsite("https://rahulshettyacademy.com");

        List<String> typesList = new ArrayList<String>();
        typesList.add("shoe park");
        typesList.add("shop");

        placeAPI.setTypes(typesList);

        Location location = new Location();
        location.setLat(-38.383594);
        location.setLng(33.427462);

        placeAPI.setLocation(location);

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        Response response = given()
                .queryParam("key", "qaclick123")
                .contentType("application/json")
                .body(placeAPI)
        .when()
                .post("maps/api/place/add/json")
        .then()
                .statusCode(200)
                .body("scope", equalTo("APP"))
                .log().all()
        .extract().response();
    }
}
