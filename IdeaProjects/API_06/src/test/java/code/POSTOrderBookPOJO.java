package code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class POSTOrderBookPOJO {
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

        // create a JSON object using JSONObject from json library
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
        String orderId = orderBookResponse.jsonPath().getString("orderId");

    }

    @Test(description = " Given a baseURI and Auth token, headers, " +
            "order a book and verify status code 201")
    void orderBookUsingPOJO() throws JsonProcessingException {
        // this is the same process to make a call using the most efficient way
        // using the structure in orderBookPOJO

        // Given
        // HTTP Method, body,headers,token, endpoint

        // Generate a token
        String authorization=utilities.generateBearerToken();

        // Request payload
        Faker faker=new Faker();
        String customerName=faker.name().fullName();
        String bookId=utilities.getABookId();

        //Create POJO using the constructor from orderBookPOJO class
        // where the values are set
        orderBookPOJO requestBody=new orderBookPOJO(bookId, customerName);

        // Instantiate an ObjectMapper to convert POJO to JSON
        ObjectMapper objectMapper=new ObjectMapper();

        // we convert the POJO into a JSON representation using ObjectMapper
        // and save it in a String after Pretty writing it to show up in JSON format
        // we will then use in the request body
        String orderJSONPayload=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestBody);
        System.out.println("The request payload will be: "+orderJSONPayload);

        //
        RequestSpecification orderBookRequest=given().
                header("Content-Type", "application/json").
                header("Authorization", authorization).
                body("orderJSONPayload");

//WHen
        Response orderBookResponsePayload=orderBookRequest.when().post("/orders");

        // then we cam make assertions
        orderBookResponsePayload.then().assertThat().statusCode(201);

        String orderId=orderBookResponsePayload.jsonPath().getString("orderId");
        System.out.println("orderId = " + orderId);

        //UPDATE ORDER

        // Create a new customerName
        String newName="Nelly";

        //
        orderBookPOJO orderBookPOJO=new orderBookPOJO(newName );

        // use objectMapper to create JSON
        ObjectMapper objectMapper1=new ObjectMapper();
        String updateOrderJSON=objectMapper1.writerWithDefaultPrettyPrinter().writeValueAsString(orderBookPOJO);

        RequestSpecification updateBookRequest=given().
                header("Content-Type", "application/json").
                header("Authorization", authorization).
                pathParam("orderId", orderId).
                body("updatedOrderJSON");

        Response updateOrderResponse=updateBookRequest.when().patch("/order/{orderId}");

        updateOrderResponse.then().assertThat().statusCode(204);

        RequestSpecification listofOrdersRequest=given().
                header("Authorization", authorization);

        Response listofOrdersResponse=listofOrdersRequest.when().get("/orders");
        listofOrdersResponse.then().assertThat().statusCode(200);

        System.out.println("listofOrdersResponse.getBody().asString() = " + listofOrdersResponse.getBody().asString());

        String actualNewCustomerName=listofOrdersResponse.jsonPath().getString("customerName");
        Assert.assertTrue(actualNewCustomerName.contains(newName));


    }
}
