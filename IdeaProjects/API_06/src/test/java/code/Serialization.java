package code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Serialization {

    //serialization(POJO) ========> jsonPayload(JSON)

    public static void main(String[] args) throws JsonProcessingException {

        // create a POJO from the class where setter and getter are located
        // setter allows you to access keys and set values
        serializationAndDeserialization serialization = new serializationAndDeserialization();

        // set the keys to the values we want
        // to have at the end a POJO with key/value pairs
        serialization.setBookId("1");
        serialization.setCustomerName("Benjamin");

        // now we need to convert to a JSON representation using objectMapper class
        ObjectMapper objectMapper=new ObjectMapper();

        // then create a String to save the JSON
        // We use a method called writerWithDefaultPrettyPrinter() to print JSON in the way its displayed in response
        // The String jsonPayload is the JSON equivalent to serialization POJO
        String jsonPayload=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(serialization);
        System.out.println("jsonPayload = " + jsonPayload);

        //           serialization(POJO) ========> jsonPayload(JSON)

    }
}
