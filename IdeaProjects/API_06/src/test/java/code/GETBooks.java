package code;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GETBooks {
    String URL = "https://simple-books-api.glitch.me/books";

String baseURI = RestAssured.baseURI="https://simple-books-api.glitch.me/";
    @Test(description = "Given baseURI we make GET call to /books and verify status code is 200")

    public void ValidateStatusCode() {
        // We will use ResAssured.get("endpoint") since baseURI is already saved as a String variable
        Response response = RestAssured.get("/books");
        int actualStatusCode = response.statusCode();
        int expectedStatusCode = 200;
        System.out.println(response.getBody().asString());
        Assert.assertTrue(actualStatusCode == expectedStatusCode);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

        @Test(description = "Given baseURI, when we make a GET call" +
                " to /books and use the querey param limit = 2, then verify status 200")

                public void userRetriveListOfBooksLimit2(){
// declaring the request sepcification
            RequestSpecification request = given().queryParam("limit",2);
            // making the call and save it in a response obj
            Response response = request.when().get(URL);
//make an assertion on the response saved
            response.then().assertThat().statusCode(200);

            System.out.println(response.getBody().asString());
            // {"id":1,"name":"The Russian","type":"fiction","available":true},
            // {"id":2,"name":"Just as I Am","type":"non-fiction","available":false},
            String actualsecondBookName = response.jsonPath().getString("[1].name");
            String expectedsecoundBookName = "Just as I Am";
            String actualsecondBookId = response.jsonPath().getString("[1].id");
            String expectedSecondBookId = "2";

            Assert.assertEquals(expectedSecondBookId,actualsecondBookId);
            Assert.assertEquals(expectedsecoundBookName, actualsecondBookName);

// we can make the same call using only 1 line of code in what it's called "method chaining":
//given().queryParam("limit",2).when().get(URL).then().assertThat().statusCode(200);

        }
        ////////////////////////////////////////////////////////////////////////////////////////////////
        @Test(description ="given() a specific query param type=crime(doesn't exist), " +
                "when() we make the GET call to /books " +
                "then() we can verify status code")

    public void verifyTypeandStausCode(){

        // the name pf the method
        //GIVEN that I have specific parameters for my request, I can create a RequestSpecification obj
        RequestSpecification request = given().queryParam("type","crime");

        //When I make a call with the RequestSpecification obj to the base URL and save it in a Response obj
        Response response = request.when().get(URL);

        // THEN I can assert from the Response obj
        response.then().assertThat().statusCode(400);
        }

    @Test(description ="multiple query param")

    public void verifyTypeandStausCodeMultParam(){  // the name pf the method
        //GIVEN that I have specific parameters for my request, I can create a RequestSpecification obj
        RequestSpecification request = given().queryParams("type","fiction","limit",1);

        //When I make a call with the RequestSpecification obj to the base URL and save it in a Response obj
        Response response = request.when().get(URL);

        // THEN I can assert from the Response obj
        response.then().assertThat().statusCode(200);
        System.out.println("response.getBody().asString() = " + response.getBody().asString());
        System.out.println("The type of this book is "+response.jsonPath().getString("[0].type")+
                " and the book id is " + response.jsonPath().getString("[0].id"));
        System.out.println(response.jsonPath().getString("[0]"));
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test(description = "path param with a hardcoded bookId")
    public void usingPathParam(){
        // i hard coded the bookId
        String bookId ="1";

        // I am creating a path param with name bookId and value 1
        RequestSpecification request = given().pathParam("bookId",bookId);
        // I am making the call and including the endpoint /books then the value of the bookId
        Response response = request.when().get("/books/{bookId}");
        response.then().statusCode(200);
        System.out.println(response.getBody().asString());
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test(description = "path param with a bookId from utilities")
    public void usingPathParamAndBooIdFromUtils(){
        // bookId from utilities
        String bookIdFromUtils = utilities.getABookId();
        // I am creating a path param with name bookId and value from utilities
        RequestSpecification request = given().pathParam("bookId",utilities.getABookId());
        // I am making the call and including the endpoint /books then the value of the bookId
        Response response = request.when().get("/books/{bookId}");
        response.then().statusCode(200);
        System.out.println(response.getBody().asString());
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////
    }



