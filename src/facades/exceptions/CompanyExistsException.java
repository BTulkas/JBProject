package facades.exceptions;

public class CompanyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CompanyExistsException() {
		super("Company already exists!");
	}

}
