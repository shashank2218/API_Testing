package mockResponse;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import payLoad.body;

public class JsonParse {
    public static void main(String[] args){
        JsonPath jsonPath = new JsonPath(body.dummyResponse());

        //Print No of courses returned by API
        int noOfCourses = jsonPath.getInt("courses.size()");
        System.out.println(noOfCourses);

        //Print Purchase Amount
        System.out.println(jsonPath.getInt("dashboard.purchaseAmount"));

        //Print Title of the first course
        System.out.println(jsonPath.getString("courses[0].title"));

        //Print All course titles and their respective Prices
        for (int i=0; i<noOfCourses; i++){
            System.out.println(jsonPath.getString("courses["+i+"].title"));
            System.out.println(jsonPath.getInt("courses["+i+"].price"));
        }

        //Print no of copies sold by RPA Course
        for (int j=0; j<noOfCourses; j++) {
            if (jsonPath.getString("courses["+j+"].title").equalsIgnoreCase("RPA")) {
                System.out.println("No of copies sold " + jsonPath.getInt("courses["+j+"].copies"));
                break;
            }
        }

        //Verify if Sum of all Course prices matches with Purchase Amount
        int sum = 0;
        for (int k=0; k<noOfCourses; k++) {
            int price = jsonPath.getInt("courses["+k+"].price");
            int copies = jsonPath.getInt("courses["+k+"].copies");
            int amount = price * copies;
            sum = sum + amount;
        }
        System.out.println(sum);

        Assert.assertEquals(jsonPath.getInt("dashboard.purchaseAmount"), sum);
    }
}
