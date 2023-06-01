package code;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class POSTOrderBook {

    // This is a flow of ordering, Update, and Delete a book
    // we order the book using GET
    // then check the list of orders to see if it's there using GET
    // then we partially update the order using PATCH
    // make another GET call to list of orders to se it's updated because PATCH returns 204 Successful with an empty response
    // then we delete the order using DELETE
    // make another GET call to list of orders to see if the order is deleted because DELETE returns 204 Successful with an empty rsponse

    String baseURI= RestAssured.baseURI="https://simple-books-api.glitch.me";

    @Test(description = "BaseURI + access token+ POST request to endpoint /orders, then verify status code")
    void orderBook(){
        // Order book call
        // to make the call we need: URI, payload, access token, endpoint, headers(content-type)

        // Get token from utils
        String token= utilities.generateBearerToken();

        // Per API docs, to POST an order we need the following payload( full name and bookId)
        Faker faker=new Faker();
        String customerName=faker.name().fullName();
        String bookId=utilities.getABookId();


        /* {"bookId":" a bookId from utils class"
        "customerName":"the name of the customer form Faker"}
         */
        // Push the previous String data into JSON format using a JSON object with key-value pairs
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("bookId",bookId);
        jsonObject.put("customerName",customerName);

        // Covert the entire JSON obj to a String payload to pass in the body
        String requestPayload=jsonObject.toString();

        // declare the headers and request body using RequestSpecification
        RequestSpecification orderBookRequest=given().
                header("Content-Type", "application/json").
                header("Authorization",token).
                body(requestPayload);

//send and send the response using the BaseURI from ResAssured plus the endpoint
        Response orderBookResponse=orderBookRequest.when().post("/orders");

        // Use then method to assert status code
        orderBookResponse.then().assertThat().statusCode(201); // Created
        System.out.println("The body of the response is" + orderBookResponse.getBody().asString());

        // Grab the orderId and save it for future use
        String orderId=orderBookResponse.jsonPath().getString("orderId");
/////////////////////////////////////__GET__///////////////////////////////////////////


        // Retrieve orders
        // let's make the second call: list of ordered book(s), this requires just an Authorization

        //Given
        RequestSpecification listOfOrdersRequest=given().header("Authorization",token);

        //When
        Response listofOrderResponse=listOfOrdersRequest.when().get("/orders");

        // Then we can make assertions
        listofOrderResponse.then().assertThat().statusCode(200); //OK
        System.out.println("The response body of GET orders (we fixed bookId value in 1 in utils"
                        +listofOrderResponse.getBody().asString());

        // We will save that same customer name to do more testing with it
        String actualCustomerName=listofOrderResponse.jsonPath().getString("customerName");
        Assert.assertTrue(actualCustomerName.contains(customerName));
        System.out.println("The expected customerName who placed the order is showing in the list of orders");

////////////////////////////////////////__UPDATE__////////////////////////////////////////

        //UPDATE let's partially update the order - Patch, per Postman we need (token, Content-Type, path param, request body)

        // We will create a new customerName
        String newCustomerName="Onder";

        // put the data in key-value JSON format
        JSONObject jsonObjectNewCustomerName=new JSONObject();
        jsonObjectNewCustomerName.put("customerName", newCustomerName);

        // Prepare the data to be added to body as String
        String updateOrderRequestPayload=jsonObjectNewCustomerName.toString();

        //Given (add the param, headers, and content of the body from the earlier line as a String
        RequestSpecification updateOrderRequest=given().
                pathParam("orderId",orderId).
                header("Content-Type","application/json").
                header("Authorization",token).
                body(updateOrderRequestPayload);
        //When, send request and save it in Response obj
        Response updatedOrderResponse=updateOrderRequest.when().patch("/orders/{orderId}");

        //Assert that the request was received successfully usually with no content 204
        updatedOrderResponse.then().assertThat().statusCode(204);

        //check if customer name was successfully changed
        System.out.println("customerName updated from "+customerName+", to "+newCustomerName);


//////////////////////////////////////////////////////////////////////////////////////

        // Repeat the same call to see if the name has been updated in the response body
        //List of Ordered Book(s)
         listOfOrdersRequest=given().
                header("Authorization", token);
        listofOrderResponse=listOfOrdersRequest.when().get("/orders");

      listofOrderResponse.then().assertThat().statusCode(200);
        System.out.println("The orders detail after update" + listofOrderResponse.getBody().asString());

        // save the customer name to compared it with what we had in the previous call
        String actualNewCustomerName=listofOrderResponse.jsonPath().getString("[0].customerName");
        // Assert

        Assert.assertTrue(newCustomerName.contains(actualNewCustomerName));


/////////////////////////////////////////////////////////////////////////////////////////

        // Now we will DELETE the order to check E2E
        // Given
        // Token, path param, Content-Type, Body(if requested)
        RequestSpecification deleteOrderRequest=given().pathParam("orderId", orderId).
                header("Content-Type","application/json").
                header("Authorization",token).
                body("{}"); // body is required but it will be empty
        // When
        Response deleteOrderResponse=deleteOrderRequest.when().delete("/orders/{orderId}");

        // Now assert
        deleteOrderResponse.then().assertThat().statusCode(204);
        System.out.println("The order details after deletion: "+deleteOrderResponse.getBody().asString());

        // Last call to verify if the book was deleted
        RequestSpecification listOfOrdersRequest2=given().
                header("Authorization", token);
        Response listofOrderResponse2=listOfOrdersRequest2.when().get("/orders");
        listofOrderResponse2.then().assertThat().statusCode(200);

        // Now let's print the body to make sure no orderId is going to appear
        System.out.println(listofOrderResponse2.getBody().asString());

        // Save the body and have some assertion
        String listofTheOrdersResponseBody=listofOrderResponse2.getBody().asString();
        Assert.assertTrue(!listofTheOrdersResponseBody.contains(orderId));

    }
}
