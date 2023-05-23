package code;

import io.restassured.RestAssured;
import io.restassured.response.Response;
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

}
