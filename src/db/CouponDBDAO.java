package db;

import beans.Coupon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CouponDBDAO implements CouponDAO {

    private ConnectionPool pool = ConnectionPool.getInstance();


    @Override
    public void addCoupon(Coupon coupon) throws SQLException {
        Connection con = pool.getConnection();

        try {
            PreparedStatement stmnt = con.prepareStatement("INSERT INTO coupons (Company_id, category, title, description, start_date, end_date, amount, price, image)) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmnt.setInt(1, coupon.getCompanyId());
            stmnt.setString(2, String.valueOf(coupon.getCategory()));
            stmnt.setString(3, coupon.getTitle());
            stmnt.setString(4, coupon.getDescription());
            stmnt.setDate(5, coupon.getStartDate());
            stmnt.setDate(6, coupon.getEndDate());
            stmnt.setInt(7, coupon.getAmount());
            stmnt.setDouble(7, coupon.getPrice());
            stmnt.setString(8, coupon.getImage());

            stmnt.execute();

        } finally {
            pool.restoreConnection(con);
        }

    }

    @Override
    public void updateCoupon(Coupon coupon) throws SQLException {
        Connection con = pool.getConnection();

        try {
            PreparedStatement stmnt = con.prepareStatement("UPDATE coupons SET Company_id=?, category=?, title=?, description=?, start_date=?, end_date=?, amount=?, price=?, image=?) WHERE coupon_id=?)");
            stmnt.setInt(1, coupon.getCompanyId());
            stmnt.setString(2, String.valueOf(coupon.getCategory()));
            stmnt.setString(3, coupon.getTitle());
            stmnt.setString(4, coupon.getDescription());
            stmnt.setDate(5, coupon.getStartDate());
            stmnt.setDate(6, coupon.getEndDate());
            stmnt.setInt(7, coupon.getAmount());
            stmnt.setDouble(7, coupon.getPrice());
            stmnt.setString(8, coupon.getImage());
            stmnt.setInt(9, coupon.getCouponId());

            stmnt.execute();

        } finally {
            pool.restoreConnection(con);
        }

    }

    @Override
    public void deleteCoupon(int couponId) throws SQLException {
        Connection con = pool.getConnection();

        try {
            PreparedStatement stmnt = con.prepareStatement("DELETE FROM coupons WHERE coupon_id = ?");

            stmnt.setInt(1, couponId);

            stmnt.execute();

        } finally {
            pool.restoreConnection(con);
        }

    }

    @Override
    public ArrayList<Coupon> getAllCoupons() throws SQLException {
        ArrayList<Coupon> coupons = new ArrayList<Coupon>(0);
        Connection con = pool.getConnection();

        try {

            PreparedStatement stmnt = con.prepareStatement("SELECT * FROM coupons");
            ResultSet rs = stmnt.executeQuery();
            while(rs.next()) {
                coupons.add(new Coupon());
            }
            return coupons;

        }finally {
            pool.restoreConnection(con);
        }    }

    @Override
    public Coupon getOneCoupon(int couponId) {
        return null;
    }

    @Override
    public void addCouponPurchase(int customerId, int couponId) {

    }

    @Override
    public void deleteCouponPurchase(int customerId, int couponId) {

    }
}
