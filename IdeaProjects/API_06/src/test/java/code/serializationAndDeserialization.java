package code;

public class serializationAndDeserialization {
    // This concept is special to RESTassured library.
    // S conversion to JSON, D conversion to Java obj
    //first we need to declare the variables with the same names in the request/response body

    // these setter and getter are going to feed objectMapper obj from Jackson library
    // to read values from the POJO we will create
    private String bookId;
    private String customerName;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
