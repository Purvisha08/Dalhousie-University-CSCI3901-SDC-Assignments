/**
 * {@code Main}
 *
 * This is the user defined / User Created Exception that is used to print the custome messages
 * to the users
 *
 * @author Purvisha Patel (B00912611)
 * Created on 2022-04-06
 * @version 1.0.0
 * @since 1.0,0
 */
public class OrderException extends Exception{

    private int referenceInteger; // used to store refereences fro which exception id generated

    /**
     * {@code OrderException} It is a constructor used to store the Error Message
     *
     * @param message Message thet needs to be stored will be passed as parameter
     *
     */
    public OrderException(String message){
        super(message);
    }

    /**
     * {@code OrderException} It is a constructor used to store the Error Message with reference
     *
     * @param message Message thet needs to be stored will be passed as parameter
     * @param referenceInteger ReferenceInteger of the message
     *
     */
    public OrderException(String message, int referenceInteger){
        super(message);
        this.referenceInteger = referenceInteger;
    }


    /**
     * {@code OrderException} It is used to get the referenece Intereger from object
     *
     * @return referenceInterger
     *
     */
    public int getReferenceInteger() {
        return referenceInteger;
    }
}
