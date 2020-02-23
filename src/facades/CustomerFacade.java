package facades;

import beans.CategoryType;
import beans.Coupon;
import beans.Customer;
import db.exceptions.CustomerNotFoundException;
import facades.exceptions.IncorrectPasswordException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class CustomerFacade extends ClientFacade {

	private int loggedCustomerId;


	// Does what it says on the tin.
	@Override
    public boolean login(String email, String password) throws SQLException, IncorrectPasswordException, CustomerNotFoundException {
        int isExists = customDB.isCustomerExists(email);

		// Checks if a customer was found by email.
		if (isExists == 0) throw new CustomerNotFoundException();
		else {
			Customer custom = customDB.getOneCustomer(isExists);
			if (custom.getPassword().equals(password)) {
				// Saves the customer ID to a variable for use in other methods
				loggedCustomerId = isExists;
						return true;
					}
			// Throws an exception if the password is incorrect against all security protocols
			else throw new IncorrectPasswordException();
		}
	}


// Coupon getter methods

	// Does what is says on the tin.
	public void purchaseCoupon(Coupon coupon) throws SQLException {

        // Checks if coupon is in stock and not expired.
		if(coupon.getAmount()!=0 && coupon.getEndDate().after(new Date())) {
			// Logs the coupon purchse.
			coupDB.addCouponPurchase(loggedCustomerId, coupon.getCouponId());
			// Decreases amount of coupon in stock by 1.
			coupon.setAmount(coupon.getAmount()-1);
			coupDB.updateCoupon(coupon);
		}
    }


    // Returns ArrayList of all coupons purchased by the logged customer.
    public ArrayList<Coupon> getCustomerPurchaseHistory() throws SQLException {

		return coupDB.getCustomerPurchaseHistory(loggedCustomerId);
	}


	// Returns ArrayList of logged customer purchased coupons in a given category.
	public ArrayList<Coupon> getCustomerPurchaseHistoryByCategory(CategoryType category) throws SQLException {
		// Creates the ArrayList to return
		ArrayList<Coupon> coupons = new ArrayList<>();

		// Iterates over all logged customer purchased coupons and adds them to coupons ArrayList on matching category.
		for(Coupon coupon : getCustomerPurchaseHistory()){
    		if(coupon.getCategory().equals(category)) coupons.add(coupon);
		}

		return coupons;
	}


	// Returns ArrayList of logged company coupons with price lower than maxPrice.
	public ArrayList<Coupon> getCustomerPurchaseHistoryByPrice(double maxPrice) throws SQLException {
		// Creates the ArrayList to return
		ArrayList<Coupon> coupons = new ArrayList<>();

		// Iterates over all logged customer purchased coupons and adds them to coupons ArrayList on price < maxPrice.
		for(Coupon coupon : getCustomerPurchaseHistory()){
    		if(coupon.getPrice() > maxPrice) coupons.add(coupon);
		}

		return coupons;
	}


	// Does what it says on the tin.
	public Customer getLoggedCustomer(int loggedCustomerId) throws CustomerNotFoundException, SQLException {
    	return customDB.getOneCustomer(loggedCustomerId);
	}

}
