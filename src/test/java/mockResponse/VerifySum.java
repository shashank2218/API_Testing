package mockResponse;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import payLoad.body;

public class VerifySum {

    @Test
    public void sumOfCourses() {
        JsonPath jsonPath = new JsonPath(body.dummyResponse());
        int noOfCourses = jsonPath.getInt("courses.size()");

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
