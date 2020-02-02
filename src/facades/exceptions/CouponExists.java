package facades.exceptions;

public class CouponExists extends Throwable {

    public CouponExists(){super("There is already a coupon by that name");}

}
