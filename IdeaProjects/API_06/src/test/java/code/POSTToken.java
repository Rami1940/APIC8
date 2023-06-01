package code;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class POSTToken {


    String baseURI= RestAssured.baseURI="https://simple-books-api.glitch.me";

    @Test(description = "RestAssured BaseURI and end point (/api-clients) to generate and access token" )
     void verifyAccessToken(){
        System.out.println(utilities.generateBearerToken());
    }

}
