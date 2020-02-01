package db.exceptions;

public class CouponNotFoundException extends Throwable {

    private static final long serialVersionUID = 3L;

    public CouponNotFoundException() {
        super("Coupon not found!");
    }

}
