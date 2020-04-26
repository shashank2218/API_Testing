package authorization;

import convert.File_Convert;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class OAuth {

    @Test
    public void getAccessToken() {

        String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2FzAH0-xOs-XfRdtuTsMs8MoSUsNen0Du9cVRUE_hCwYicbU4CdVA_eAZkp-B7C2Du2Wh4axUzRDKCxYRuGjY3x-o&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent#";
        String partialURL = url.split("code=")[1];
        String code = partialURL.split("&scope")[0];
        System.out.println(code);

        Response response =
        given()
                //.urlEncodingEnabled(false)
                .queryParams("code", code)
                .queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParams("redirect_url", "https://rahulshettyacademy.com/getCourse.php")
                .queryParams("grant_type", "authorization_code")
        .when()
                .post("https://www.googleapis.com/oauth2/v4/token")
        .then()
        .extract().response();

        JsonPath jsonPath = File_Convert.rawToJson(response);
        String accessCode = jsonPath.get("access_token");


        String resp =
        given()
                .queryParam("access_token", accessCode)
        .when()
                .get("https://rahulshettyacademy.com/getCourse.php")
                .asString();

        System.out.println(resp);

    }
}
