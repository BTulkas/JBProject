package db.exceptions;

public class CustomerNotFoundException extends Exception {

    private static final long serialVersionUID = 2L;

    public CustomerNotFoundException() {
        super("Customer not found!");
    }
}
