package facades.exceptions;

public class CustomerExistsException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomerExistsException() {
        super("Customer already exists!");
    }
}
