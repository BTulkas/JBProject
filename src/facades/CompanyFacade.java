package facades;

import beans.CategoryType;
import beans.Company;
import beans.Coupon;
import db.CompanyDBDAO;
import db.CouponDBDAO;
import db.exceptions.CompanyNotFoundException;
import db.exceptions.CustomerNotFoundException;
import facades.exceptions.CouponExists;
import facades.exceptions.IncorrectPasswordException;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompanyFacade extends ClientFacade {


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


    public void updateCoupon(Coupon coupon) throws SQLException {

        coupDB.updateCoupon(coupon);

    }

    
    public void deleteCoupon(int couponId) throws SQLException, CustomerNotFoundException {
    	
    	coupDB.deleteCouponPurchase(coupDB.getBuyerId(couponId), couponId);
    	
    	coupDB.deleteCoupon(couponId);
    }


    // Coupon getter methods
    public ArrayList<Coupon> getCompanyCoupons(int companyId) throws SQLException {

        return coupDB.getCompanyCoupons(companyId);
    }


    public ArrayList<Coupon> getCompanyCouponsByCategory(int companyId, CategoryType category) throws SQLException {
        ArrayList<Coupon> coupons = coupDB.getCompanyCoupons(companyId);

        for(Coupon coupon:coupons){
            if(coupon.getCategory() != category) coupons.remove(coupon);
        }
        return coupons;
    }


    public ArrayList<Coupon> getCompanyCouponsByMaxPrice(int companyId, double maxPrice) throws SQLException {
        ArrayList<Coupon> coupons = coupDB.getCompanyCoupons(companyId);

        for(Coupon coupon:coupons){
            if(coupon.getPrice() != maxPrice) coupons.remove(coupon);
        }
        return coupons;
    }


/*    No idea what this is supposed to return.
    public ??? GetCompanyDetails(???){

    }*/

}
