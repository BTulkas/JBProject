package db;

import beans.Coupon;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CouponDAO {

    void addCoupon(Coupon coupon) throws SQLException;
    void updateCoupon(Coupon coupon) throws SQLException;
    void deleteCoupon(int couponId) throws SQLException;
    ArrayList<Coupon> getAllCoupons() throws SQLException;
    Coupon getOneCoupon(int couponId);
    void addCouponPurchase(int customerId, int couponId);
    void deleteCouponPurchase(int customerId, int couponId);

}
