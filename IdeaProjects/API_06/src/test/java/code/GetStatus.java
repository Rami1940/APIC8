package code;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetStatus {

    @Test
    public void happyPathTest(){
        // this line to make the call and save the response in the response obj we created
        Response response = RestAssured.get("https://simple-books-api.glitch.me/status");

        System.out.println("Response: "+response.asString());

        System.out.println("response.getStatusCode() = " + response.getStatusCode());
        System.out.println("response.getTime() = " + response.getTime());
        System.out.println("response.getBody() = " + response.getBody());
        System.out.println("response.getHeader(\"Content-Type\") = " + response.getHeader("Content-Type"));
        System.out.println("response.getHeader(\"Content-Length\") = " + response.getHeader("Content-Length"));
        System.out.println("response.getHeader(\"Date\") = " + response.getHeader("Date"));
        System.out.println("response.getHeader(\"Test Header\") = " + response.getHeader("Test Header"));
        System.out.println("response.getHeaders() = " + response.getHeaders());
    }

    @Test(description = "Given baseURI Whem we make the get call" +
            " to /status enpoint Then verifystatus code")
public void validateStatusCode(){
        Response response = RestAssured.get("https://simple-books-api.glitch.me/status");
        //here we made a GET call
        int actualStatusCode = response.getStatusCode();
        int expectedStatusCode = 200;
        System.out.println("actualStatusCode = " + actualStatusCode);
        System.out.println("expectedStatusCode = " + expectedStatusCode);
       Assert.assertEquals(expectedStatusCode,actualStatusCode);
    }
    @Test(description = "Given baseURI we make a get call to /status " +
            "enpoint then verify time of response is less than 2000")
    public void ValidateTime(){
        Response response = RestAssured.get("https://simple-books-api.glitch.me/status");
        long actualResponseTime = response.getTime();
        System.out.println("actualResponseTime = " + actualResponseTime);
        Assert.assertTrue(actualResponseTime<2000);

    }
}
