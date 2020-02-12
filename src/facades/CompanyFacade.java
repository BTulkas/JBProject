package facades;

import beans.CategoryType;
import beans.Company;
import beans.Coupon;

import db.exceptions.CompanyNotFoundException;
import db.exceptions.CouponNotFoundException;
import db.exceptions.CustomerNotFoundException;
import facades.exceptions.CouponExists;
import facades.exceptions.IncorrectPasswordException;

import java.sql.SQLException;
import java.util.ArrayList;

public class CompanyFacade extends ClientFacade {

	private int loggedCompanyId;

    @Override
    public boolean login(String email, String password) throws SQLException, CompanyNotFoundException, IncorrectPasswordException {
        int isExists = compDB.isCompanyExists(email, password);

		if (isExists == 0) throw new CompanyNotFoundException();
		else {
			Company comp = compDB.getOneCompany(isExists);
					if (comp.getPassword().contentEquals(password)) {
						loggedCompanyId = isExists;
						return true;
					}
					else throw new IncorrectPasswordException();
		}
	}
			


// Coupon setter methods.
    public void addCoupon(Coupon coupon) throws SQLException, CouponExists {
       ArrayList<Coupon> coupons = coupDB.getCompanyCoupons(loggedCompanyId);

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
    public ArrayList<Coupon> getCompanyCoupons() throws SQLException {

        return coupDB.getCompanyCoupons(loggedCompanyId);
    }
    
    
    public Coupon getOneCoupon(int couponId) throws SQLException, CouponNotFoundException {
    	ArrayList<Coupon> coupons = coupDB.getCompanyCoupons(loggedCompanyId);
    	
    	Coupon coupon = coupDB.getOneCoupon(couponId);
    	
    	if(coupons.contains(coupon)) return coupon;
    	else throw new CouponNotFoundException();
    	
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


    public Company getLoggedCompanyDetails() throws SQLException, CompanyNotFoundException{
    	
    	return compDB.getOneCompany(loggedCompanyId);

    }

}
