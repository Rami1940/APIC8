package code;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;
import utilities.PropertiesReading;

import static io.restassured.RestAssured.given;

public class GETBooks_NegativeTestCase {

    @Test(description = "negative test - use type = crime (doesn't exist)")
    public void NegativeTest(){

        RequestSpecification request = given().queryParam("type","crime");
        Response response = request.when().get(PropertiesReading.getProperties("URL"));
        response.then().statusCode(400);
        System.out.println(response.getStatusCode());
    }

    @Test(description = "negative test - use a query param out of limit")
    public void NegativeTestLimitBookNumber(){

        RequestSpecification request = given().queryParam("limit","220");
        Response response = request.when().get(PropertiesReading.getProperties("URL"));
        response.then().statusCode(400);
        System.out.println(response.getStatusCode());
    }



}
