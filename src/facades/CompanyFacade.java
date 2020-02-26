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


    // Does what it says on the tin.
    @Override
    public boolean login(String email, String password) throws SQLException, CompanyNotFoundException, IncorrectPasswordException {
	    int isExists = compDB.isCompanyExists(email);

		// Checks if a company was found by email.
	    if (isExists == 0) throw new CompanyNotFoundException();
		else {
			// Checks if password matches the email.
		    Company comp = compDB.getOneCompany(isExists);
					if (comp.getPassword().equals(password)) {
						// Saves the company ID to a variable for use in other methods
					    loggedCompanyId = isExists;
						return true;
					}
					// Throws an exception if the password is incorrect against all security protocols
					else throw new IncorrectPasswordException();
		}
	}
			


// Coupon setter methods.


    // Adds a coupon.
    public void addCoupon(Coupon coupon) throws SQLException, CouponExists {

        // Iterates over all coupons by the company to throw exception if tying to add a coupon with existing name
	    for(Coupon coup:coupDB.getCompanyCoupons(loggedCompanyId)){
           if(coup.getTitle().equals(coupon.getTitle())){
               throw new CouponExists();
           }
       }
        coupDB.addCoupon(coupon);
    }


    // Updates coupon to match Coupon object given.
    public void updateCoupon(Coupon coupon) throws SQLException, CouponNotFoundException {

    	// Checks that the coupon requested belongs to the logged company.
    	if(coupon.getCompanyId() == loggedCompanyId) coupDB.updateCoupon(coupon);
    	else throw new CouponNotFoundException();
    	

    }

    
    // Does what it says on the tin.
    public void deleteCoupon(int couponId) throws SQLException, CouponNotFoundException {

    	// Checks that the coupon requested belongs to the logged company.
    	if(getOneCoupon(couponId).getCompanyId() == loggedCompanyId) {
	    	// Deletes record of all purchases of the coupon for some reason.
	        coupDB.deleteCouponPurchase(couponId);
	    	
	    	coupDB.deleteCoupon(couponId);
    	}
    	else throw new CouponNotFoundException();
    }
    	



// Coupon getter methods


    // Returns ArrayList of all coupons by the logged in company.
    public ArrayList<Coupon> getCompanyCoupons() throws SQLException {

        return coupDB.getCompanyCoupons(loggedCompanyId);
    }
    
    
    // Returns a single Coupon object by ID.
    public Coupon getOneCoupon(int couponId) throws SQLException, CouponNotFoundException {
    	Coupon coupon = coupDB.getOneCoupon(couponId);

    	// Checks that the coupon requested belongs to the logged company.
    	if(coupon.getCompanyId() == loggedCompanyId) return coupon;
    	else throw new CouponNotFoundException();
    	
    }


    // Returns ArrayList of logged company coupons in a given category.
    public ArrayList<Coupon> getCompanyCouponsByCategory(CategoryType category) throws SQLException {
        // Creates the ArrayList to return
        ArrayList<Coupon> coupons = new ArrayList<>();

        // Iterates over all coupons by the logged company and adds them to coupons ArrayList on matching category.
        for(Coupon coupon:coupDB.getCompanyCoupons(loggedCompanyId)){
            if(coupon.getCategory().equals(category)) coupons.add(coupon);
        }
        return coupons;
    }


    // Returns ArrayList of logged company coupons with price lower than maxPrice.
    public ArrayList<Coupon> getCompanyCouponsByMaxPrice(double maxPrice) throws SQLException {
        // Creates the ArrayList to return
        ArrayList<Coupon> coupons = new ArrayList<>();

        // Iterates over all coupons by the logged company and adds them to coupons ArrayList on price < maxPrice.
        for(Coupon coupon:coupDB.getCompanyCoupons(loggedCompanyId)){
            if(coupon.getPrice() < maxPrice) coupons.add(coupon);
        }
        return coupons;
    }


    // Does what it says on the tin.
    public Company getLoggedCompanyDetails() throws SQLException, CompanyNotFoundException{
    	
    	return compDB.getOneCompany(loggedCompanyId);

    }

}
