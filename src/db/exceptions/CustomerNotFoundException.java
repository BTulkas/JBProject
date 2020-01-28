package db.exceptions;

public class CustomerNotFoundException extends Throwable {

    private static final long serialVersionUID = 2L;

    public CustomerNotFoundException() {
        super("Company not found!");
    }
}
