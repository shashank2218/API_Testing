package specBuilder;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import pojo.Location;
import pojo.PlaceAPI;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SpecPlaceAPI {

    @Test
    public void specBuildPlaceAPI () {

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

        RequestSpecification request = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123")
                .setContentType("application/json")
                .build();

        ResponseSpecification response = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectBody("scope", equalTo("APP"))
                .build();

        given()
                .spec(request)
                .body(placeAPI)
        .when()
                .post("maps/api/place/add/json")
        .then()
                .spec(response)
                .log().all()
        .extract().response();
    }
}
