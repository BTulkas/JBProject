package db;

import beans.Coupon;
import db.exceptions.CouponNotFoundException;
import db.exceptions.CustomerNotFoundException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CouponDAO {

    void addCoupon(Coupon coupon) throws SQLException;
    void updateCoupon(Coupon coupon) throws SQLException;
    void deleteCoupon(int couponId) throws SQLException;
    ArrayList<Coupon> getAllCoupons() throws SQLException;
    ArrayList<Coupon> getCompanyCoupons(int companyId) throws SQLException;
    Coupon getOneCoupon(int couponId) throws SQLException, CouponNotFoundException;
    void addCouponPurchase(int customerId, int couponId) throws SQLException;
    ArrayList<Coupon> getCustomerPurchaseHistory(int customerId) throws SQLException;
    void deleteCouponPurchase(int couponId) throws SQLException;

    // Used when deleting a customer's purchase history.
    void deleteCouponPurchaseByCustomer(int customerId) throws SQLException;
}
