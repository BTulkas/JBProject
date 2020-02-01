package tests;

import beans.CategoryType;
import beans.Coupon;
import db.CouponDBDAO;
import db.ConnectionPool;
import db.CouponDBDAO;
import db.exceptions.CouponNotFoundException;

import java.sql.Date;
import java.sql.SQLException;

public class TestCoupon {

    public static void main(String[] args) {

        CouponDBDAO coupDB = new CouponDBDAO();

        try {
            coupDB.addCoupon(new Coupon(1, CategoryType.Electronics, "Electro Coupon", "Discount on electro-stuff", Date.valueOf("2019-09-20"),
            Date.valueOf("2020-02-14"), 1, 10.5, "A Image"));

            System.out.println(coupDB.getAllCoupons());

        } catch (SQLException e) {
            System.out.println("Add failed! " + e.getMessage());
        }

        /*try {
            System.out.println(coupDB.getOneCoupon(1));
            System.out.println(coupDB.getOneCoupon(2));
        } catch (SQLException | CouponNotFoundException e) {
            System.out.println("Error! " + e.getMessage());
        }*/

        ConnectionPool.getInstance().CloseAllConnections();

    }

}
