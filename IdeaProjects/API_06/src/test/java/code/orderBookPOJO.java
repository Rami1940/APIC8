package code;

public class orderBookPOJO {
    // this is the most efficient way
    // here we will create the whole structure
    // and use this same structure to perform other calls


    // we don't need setter and getter methods because we will make
    // these values public. And we will set the values during the object creation
    public String bookId;
    public String customerName;

    // create a constructor
    public orderBookPOJO(String bookId,String customerName){
        this.bookId=bookId;
        this.customerName=customerName;
    }
    // create other constructor with only customerName
    public orderBookPOJO(String customerName){
        this.customerName=customerName;
    }
}
