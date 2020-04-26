package google_Place_API;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import payLoad.body;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class End_to_End {

    Properties properties = new Properties();
    @BeforeTest
    public void getPropData() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("src/main/java/prop/Base.properties");
        properties.load(fileInputStream);
    }

    @Test
    public void addAndDel() {

        Response response = given()
                .queryParam("key",properties.getProperty("KEY"))
                .body(body.postBody())
        .when()
                .post(properties.getProperty("HOST"))
        .then()
                .statusCode(200)
        .extract()
                .response();

        String strResp = response.asString();
        System.out.println(strResp);

        JsonPath jp = new JsonPath(strResp);
        String placeid = jp.get("place_id");
        System.out.println(placeid);

        given()
                .queryParam("key","qaclick123")
                .body("{"+
                        "\"place_id\": \""+placeid+"\""+
                        "}")
        .when()
                .post("http://216.10.245.166/maps/api/place/delete/json")
        .then()
                .statusCode(200)
                .log().all();

    }
}
