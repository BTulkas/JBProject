package facades.exceptions;

public class CustomerExistsException extends Exception {

    public CustomerExistsException() {
        super("Customer already exists!");
    }
}
