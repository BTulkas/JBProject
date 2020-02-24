package facades.exceptions;

public class CouponExists extends Throwable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CouponExists(){super("There is already a coupon by that name");}

}
