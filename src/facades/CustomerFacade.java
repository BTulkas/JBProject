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

    @Override
    public boolean login(String email, String password) throws SQLException, IncorrectPasswordException, CustomerNotFoundException {
        int isExists = customDB.isCustomerExists(email, password);

		if (isExists == 0) throw new CustomerNotFoundException();
		else {
			Customer custom = customDB.getOneCustomer(isExists);
					if (custom.getPassword().equals(password)) {
						loggedCustomerId = isExists;
						return true;
					}
					else throw new IncorrectPasswordException();
		}
	}


    // Coupon getter methods
	public void purchaseCoupon(Coupon coupon) throws SQLException {

        if(coupon.getAmount()!=0 && coupon.getEndDate().after(new Date())) {
        	coupon.setAmount(coupon.getAmount()-1);
			coupDB.addCouponPurchase(loggedCustomerId, coupon.getCouponId());
			coupDB.updateCoupon(coupon);
		}
    }


    public ArrayList<Coupon> getCustomerPurchaseHistory() throws SQLException {

		return coupDB.getCustomerPurchaseHistory(loggedCustomerId);
	}


	public ArrayList<Coupon> getCustomerPurchaseHistoryByCategory(CategoryType category) throws CustomerNotFoundException, SQLException {
    	ArrayList<Coupon> coupons = getCustomerPurchaseHistory();

    	for(Coupon coupon:coupons){
    		if(coupon.getCategory() != category) coupons.remove(coupon);
		}

		return coupons;
	}

	public ArrayList<Coupon> getCustomerPurchaseHistoryByPrice(int maxPrice) throws CustomerNotFoundException, SQLException {
    	ArrayList<Coupon> coupons = getCustomerPurchaseHistory();

    	for(Coupon coupon:coupons){
    		if(coupon.getPrice() > maxPrice) coupons.remove(coupon);
		}

		return coupons;
	}

	public Customer getLoggedCustomer(int loggedCustomerId) throws CustomerNotFoundException, SQLException {
    	return customDB.getOneCustomer(loggedCustomerId);
	}

}
