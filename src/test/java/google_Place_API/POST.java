package google_Place_API;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class POST {

    @Test
    public void createPlace() {

        String b ="{\n" +
                "  \"location\": {\n" +
                "    \"lat\": 33.8669710,\n" +
                "    \"lng\": -151.1958750\n" +
                "  },\n" +
                "  \"accuracy\": 50,\n" +
                "  \"name\": \"Google Shoes!\",\n" +
                "  \"phone_number\": \"(02) 9374 4000\",\n" +
                "  \"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\",\n" +
                "  \"types\": [\"shoe_store\"],\n" +
                "  \"website\": \"http://www.google.com.au/\",\n" +
                "  \"language\": \"en-AU\"\n" +
                "}";

        given()
                .queryParam("key","qaclick123")
                .body(b)
        .when()
                .post("http://216.10.245.166/maps/api/place/add/json")
        .then()
                .statusCode(200);
    }
}
