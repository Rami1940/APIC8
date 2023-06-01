package code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class Deserialization {
    public static void main(String[] args) throws JsonProcessingException {
        // SUMMARY:
        // we got a JSON response from response body,
        // we save it in a String
        // then we either use objectMapper class from Jackson library or a Map to read values
        /*
        let's assume that we have a JSON body that we want to deserialize to a POJO
        {
        "bookId" : 1,
        "customerName" : "Yaseen"
        }
        we save this response is saved as a String
         */


        String jsonObject ="{\n" +
                "        \"bookId\" : 1,\n" +
                "        \"customerName\" : \"Yaseen\"\n" +
                "        }";

        // Create an object of ObjectMapper from Jackson library
        // to read values from the saved String
        ObjectMapper objectMapper=new ObjectMapper();

        // create an obj "deserialization" below from the same class as setter and getter
        serializationAndDeserialization deserialization=objectMapper.readValue(jsonObject, serializationAndDeserialization.class);

        // we can now use the objectMapper to access values in the String using setter and getter
         System.out.println("bookId = " + deserialization.getBookId());
        System.out.println("customerName = " + deserialization.getCustomerName());

        // OR

        // create Map with key/value pairs and access what we want
        Map<String, Object> orderAsMap=objectMapper.readValue(jsonObject, Map.class);
        System.out.println("bookId with Map : " + orderAsMap.get("bookId"));
        System.out.println("customerName wit Map : "+orderAsMap.get("customerName"));
    }
}
