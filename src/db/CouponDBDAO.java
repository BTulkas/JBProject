package db;

import beans.CategoryType;
import beans.Coupon;
import db.exceptions.CouponNotFoundException;
import db.exceptions.CustomerNotFoundException;

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
            PreparedStatement stmnt = con.prepareStatement("INSERT INTO coupons (company_id, category_id, title, description, start_date, end_date, amount, price, image) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmnt.setInt(1, coupon.getCompanyId());
            stmnt.setInt(2, coupon.getCategory().ordinal()+1);
            stmnt.setString(3, coupon.getTitle());
            stmnt.setString(4, coupon.getDescription());
            stmnt.setDate(5, coupon.getStartDate());
            stmnt.setDate(6, coupon.getEndDate());
            stmnt.setInt(7, coupon.getAmount());
            stmnt.setDouble(8, coupon.getPrice());
            stmnt.setString(9, coupon.getImage());

            stmnt.execute();

        } finally {
            pool.restoreConnection(con);
        }

    }

    @Override
    public void updateCoupon(Coupon coupon) throws SQLException {
        Connection con = pool.getConnection();

        try {
            PreparedStatement stmnt = con.prepareStatement("UPDATE coupons SET category_id=?, title=?, description=?, start_date=?, end_date=?, amount=?, price=?, image=? WHERE coupon_id=?");
            stmnt.setInt(1, coupon.getCategory().ordinal()+1);
            stmnt.setString(2, coupon.getTitle());
            stmnt.setString(3, coupon.getDescription());
            stmnt.setDate(4, coupon.getStartDate());
            stmnt.setDate(5, coupon.getEndDate());
            stmnt.setInt(6, coupon.getAmount());
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
                coupons.add(new Coupon(
                        rs.getInt("coupon_id"),
                        rs.getInt("company_id"),
                        CategoryType.values()[rs.getInt("category_id")-1],
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getInt("amount"),
                        rs.getDouble("price"),
                        rs.getString("image")));
            }
            return coupons;

        }finally {
            pool.restoreConnection(con);
        }
        }


    @Override
    public ArrayList<Coupon> getCompanyCoupons(int companyId) throws SQLException {
        ArrayList<Coupon> coupons = new ArrayList<Coupon>(0);
        Connection con = pool.getConnection();

        try {

            PreparedStatement stmnt = con.prepareStatement("SELECT * FROM coupons WHERE company_id = " + companyId);
            ResultSet rs = stmnt.executeQuery();
            while(rs.next()) {
                coupons.add(new Coupon(
                        rs.getInt("coupon_id"),
                        rs.getInt("company_id"),
                        CategoryType.values()[rs.getInt("category_id")-1],
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getInt("amount"),
                        rs.getDouble("price"),
                        rs.getString("image")));
            }
            return coupons;

        }finally {
            pool.restoreConnection(con);
        }
        }

    @Override
    public Coupon getOneCoupon(int couponId) throws SQLException, CouponNotFoundException {
        Connection con = pool.getConnection();

        try {

            PreparedStatement stmnt = con.prepareStatement("SELECT * FROM coupons WHERE coupon_id = " + couponId);
            ResultSet rs = stmnt.executeQuery();
            if(rs.first()) {
                return new Coupon(
                        couponId,
                        rs.getInt("company_id"),
                        CategoryType.values()[rs.getInt("category_id")],
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getInt("amount"),
                        rs.getDouble("price"),
                        rs.getString("image")
                );
            }else throw new CouponNotFoundException();

        }finally {
            pool.restoreConnection(con);
        }
    }

    
    @Override
    public void addCouponPurchase(int customerId, int couponId) throws SQLException {
        Connection con = pool.getConnection();

        try {
            PreparedStatement stmnt = con.prepareStatement("INSERT INTO costumers_vs_coupons (customer_id, coupon_id) VALUES(?, ?)");
            stmnt.setInt(1, customerId);
            stmnt.setInt(2, couponId);

            stmnt.execute();

        } finally {
            pool.restoreConnection(con);
        }

    }


    @Override
    public ArrayList<Coupon> getCustomerPurchaseHistory(int customerId) throws SQLException {
        ArrayList<Coupon> coupons = new ArrayList<Coupon>(0);
        Connection con = pool.getConnection();

        try {

            PreparedStatement stmnt = con.prepareStatement(
                    "SELECT * FROM coupons JOIN customers_vs_coupons ON coupons.coupon_id = customers_vs_coupons.coupon_id " +
                            "WHERE customers_vs_coupons.customer_id = " + customerId);
            ResultSet rs = stmnt.executeQuery();
            while(rs.next()) {
                coupons.add(new Coupon(
                        rs.getInt("coupon_id"),
                        rs.getInt("company_id"),
                        CategoryType.values()[rs.getInt("category_id")-1],
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getInt("amount"),
                        rs.getDouble("price"),
                        rs.getString("image")));
            }
            return coupons;

        }finally {
            pool.restoreConnection(con);
        }
    }


    // Used when deleting a single coupon or a whole company.
    @Override
    public void deleteCouponPurchase(int couponId) throws SQLException {
        Connection con = pool.getConnection();

        try {
            PreparedStatement stmnt = con.prepareStatement(
                    "DELETE FROM customers_vs_coupons WHERE coupon_id = "+ couponId);

            stmnt.execute();

        } finally {
            pool.restoreConnection(con);
        }

    }


    // Used when deleting a customer's purchase history.
    @Override
    public void deleteCouponPurchaseByCustomer(int customerId) throws SQLException {
        Connection con = pool.getConnection();

        try {
            PreparedStatement stmnt = con.prepareStatement(
                    "DELETE FROM customers_vs_coupons WHERE customer_id = "+ customerId);

            stmnt.execute();

        } finally {
            pool.restoreConnection(con);
        }

    }


}
