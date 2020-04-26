package pojoDemo;

import convert.File_Convert;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.API;
import pojo.GetCourse;
import pojo.WebAutomation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class PojoGetCourse {

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


        GetCourse getCourse =
                given()
                        .queryParam("access_token", accessCode)
                        .expect().defaultParser(Parser.JSON)
                .when()
                        .get("https://rahulshettyacademy.com/getCourse.php")
                        .as(GetCourse.class);

        System.out.println(getCourse.getLinkedIn());

        //Get API course price for soap ui course
        List<API> apiCourses = getCourse.getCourses().getApi();

        for (int i=0; i<apiCourses.size(); i++) {
            if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
                System.out.println(apiCourses.get(i).getPrice());
            }
        }

        //Get all course title for webAutomation in an array list and compare
        String[] courseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};
        ArrayList<String> actualList = new ArrayList<String>();
        List<WebAutomation> webAutomationCourses = getCourse.getCourses().getWebAutomation();

        for (int j=0; j<webAutomationCourses.size(); j++) {
            actualList.add(webAutomationCourses.get(j).getCourseTitle());
        }

        List<String> expectedList = Arrays.asList(courseTitles);

        Assert.assertTrue(actualList.equals(expectedList));
    }
}
