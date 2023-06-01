package code;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class x__Experimental_class {
    String baseURI = RestAssured.baseURI = "https://simple-books-api.glitch.me";

    @Test(description = "BaseURI + access token+ POST request to endpoint /orders, then verify status code")
    void orderBook() {
        // Order book call
        // to make the call we need: URI, payload, access token, endpoint, headers(content-type)

        // Get token from utils
        String token = utilities.generateBearerToken();

        // Per API docs, to POST an order we need the following payload( full name and bookId)
        Faker faker = new Faker();
        String customerName = faker.name().fullName();
        String bookId = utilities.getABookId();

        // Put the previous String data in JSON format using a JSON object with key-value pairs
        /* {"bookId":" a bookId from utils class"
        "customerName":"the name of the customer form Faker"}
         */

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bookId", bookId);
        jsonObject.put("customerName", customerName);

        // Covert the entire JSON obj to a String payload to pass in the body
        String requestPayload = jsonObject.toString();

        // declare the headers and request body using RequestSpecification
        RequestSpecification orderBookRequest = given().
                header("Content-Type", "application/json").
                header("Authorization", token).
                body(requestPayload);

//send and send the response using the BaseURI from ResAssured plus the endpoint
        Response orderBookResponse = orderBookRequest.when().post("/orders");

        // Use then method to assert status code
        orderBookResponse.then().assertThat().statusCode(201); // Created
        System.out.println("The body of the response is" + orderBookResponse.getBody().asString());
// Grab the orderId and save it for future use
        String orderId=orderBookResponse.jsonPath().getString("orderId");
        // Retrieve orders
        // let's make the second call: list of ordered book(s)
        System.out.println("The orderId is: "+orderId);

        RequestSpecification listOfOrdersRequest=given().header("Authorization",token);

        //When
        Response listofOrderResponse=listOfOrdersRequest.when().get("/orders");

        // Then we can make assertions
        listofOrderResponse.then().assertThat().statusCode(200);
        System.out.println("My second print"+listofOrderResponse.getBody().asString());
// We will save that same customer name to do more testing with it
        String actualCustomerName=listofOrderResponse.jsonPath().getString("customerName");
        Assert.assertTrue(actualCustomerName.contains(customerName));
        System.out.println("The actual customer name is the expected actual name");


    }
}
