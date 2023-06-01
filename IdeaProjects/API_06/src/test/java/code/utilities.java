package code;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import static io.restassured.RestAssured.given;


// we need static methods to be accessed across the package

public class utilities {
    // this one is to retun the bookId of the first set
    public static String getABookId(){
        //getting the baseURI from RestAssured, so only endpoint is required
        Response response= RestAssured.get("/books");
        response.then().assertThat().statusCode(200);
        //saving the bookId of the first set of data
        String bookId=response.jsonPath().getString("[0].id");
        return bookId;
    }

    //////////////////////////////////////

    // generate an access token for POST request
    public static String generateBearerToken(){

        // Building the POST request body
        // We need two key-value pairs to send
        Faker faker=new Faker();
        String clientName=faker.name().fullName();
        String clientEmail=faker.internet().emailAddress();

        // using the JSON library we create a set of key-value pairs
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("clientName",clientName);
        jsonObject.put("clientEmail",clientEmail);

        //converting the request payload to String makes the process easier
        String requestPayload=jsonObject.toString();

        //Given
        // we must provide 3 things: request body, Headers required from API docs, any Params (path, query)
        RequestSpecification generateTokenRequest=given().header("Content-Type","application/json").body(requestPayload);

        // save the response, just like get() we are using the end point because baseURI is provided by RestAssured String
        // the endpoint "/api-clients" is provided in API docs
        Response generateTokenResponse=generateTokenRequest.when().post("/api-clients");

        // assert that the request was successful using then()
        generateTokenResponse.then().assertThat().statusCode(201);

        // the response body will have the pair key-value "accessToken": "df54g6r6g565g4t86s56t4gqge8r6w5ef3"
        // so we must access the value of the key "accessToken"

        // and in headers we have the key "Authorization" and value "Bearer d5gf4d5g46e8rtg546edgetg4e5r4frfre6"
        // so we must return a format accepted by the header
        System.out.println(clientName);
        System.out.println(clientEmail);
        System.out.println("The customer token: "+generateTokenResponse.jsonPath().getString("accessToken"));
        return "Bearer "+generateTokenResponse.jsonPath().getString("accessToken");

        // this access token is associated with the credentials generated from Java Faker


    }
}
