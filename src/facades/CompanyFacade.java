package facades;

import beans.CategoryType;
import beans.Company;
import beans.Coupon;
import db.CompanyDBDAO;
import db.CouponDBDAO;
import db.exceptions.CompanyNotFoundException;
import db.exceptions.CouponNotFoundException;
import facades.exceptions.CouponExists;
import facades.exceptions.IncorrectPasswordException;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompanyFacade extends ClientFacade {

    private CompanyDBDAO compDB = new CompanyDBDAO();
    private CouponDBDAO coupDB = new CouponDBDAO();


    @Override
    public boolean login(String email, String password) throws SQLException, CompanyNotFoundException, IncorrectPasswordException {
        ArrayList<Company> companies = compDB.getAllCompanies();

        for (Company comp : companies) {
            if (comp.getEmail() == email) {
                if (comp.getPassword() == password) break;
                else throw new IncorrectPasswordException();
            }
        }

        if(!compDB.isCompanyExists(email, password)) throw new CompanyNotFoundException();
        else return true;
    }

// Coupon setter methods.
    public void addCoupon(Coupon coupon) throws SQLException, CouponExists {
       ArrayList<Coupon> coupons = coupDB.getCompanyCoupons(coupon.getCompanyId());

       for(Coupon coup:coupons){
           if(coup.getTitle() == coupon.getTitle()){
               throw new CouponExists();
           } else{
            coupDB.addCoupon(coupon);
           }
        }
    }


    public void updateCouponTitle(Coupon coupon, String title) throws SQLException {

        Coupon coupon_to_update = coupon;

        coupon_to_update.setTitle(title);

        coupDB.updateCoupon(coupon_to_update);

    }


    public void updateCouponCategory(Coupon coupon, CategoryType category) throws SQLException {

        Coupon coupon_to_update = coupon;

        coupon_to_update.setCategory(category);

        coupDB.updateCoupon(coupon_to_update);

    }

    public void updateCouponDescription(Coupon coupon, String description) throws SQLException {

        Coupon coupon_to_update = coupon;

        coupon_to_update.setDescription(description);

        coupDB.updateCoupon(coupon_to_update);

    }

    public void updateCouponStartDate(Coupon coupon, Date startDate) throws SQLException {

        Coupon coupon_to_update = coupon;

        coupon_to_update.setStartDate(startDate);

        coupDB.updateCoupon(coupon_to_update);

    }

    public void updateCouponEndDate(Coupon coupon, Date endDate) throws SQLException {

        Coupon coupon_to_update = coupon;

        coupon_to_update.setEndDate(endDate);

        coupDB.updateCoupon(coupon_to_update);

    }

    public void updateCouponCategory(Coupon coupon, int amount) throws SQLException {

        Coupon coupon_to_update = coupon;

        coupon_to_update.setAmount(amount);

        coupDB.updateCoupon(coupon_to_update);

    }

    public void updateCouponCategory(Coupon coupon, double price) throws SQLException {

        Coupon coupon_to_update = coupon;

        coupon_to_update.setPrice(price);

        coupDB.updateCoupon(coupon_to_update);

    }

    public void updateCouponCategory(Coupon coupon, String image) throws SQLException {

        Coupon coupon_to_update = coupon;

        coupon_to_update.setImage(image);

        coupDB.updateCoupon(coupon_to_update);

    }

}
