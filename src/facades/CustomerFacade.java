package facades;

import beans.CategoryType;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import db.CouponDBDAO;
import db.CustomerDBDAO;
import db.exceptions.CompanyNotFoundException;
import db.exceptions.CustomerNotFoundException;
import facades.exceptions.IncorrectPasswordException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CustomerFacade extends ClientFacade {

	private int loggedCustomerId;

    @Override
    public boolean login(String email, String password) throws SQLException, IncorrectPasswordException, CustomerNotFoundException {
        int isExists = customDB.isCustomerExists(email, password);

		if (isExists == 0) throw new CustomerNotFoundException();
		else {
			Customer custom = customDB.getOneCustomer(isExists);
					if (custom.getPassword() == password) {
						loggedCustomerId = isExists;
						return true;
					}
					else throw new IncorrectPasswordException();
		}
	}


    // Coupon getter methods
	public void purchaseCoupon(Coupon coupon) throws SQLException {

        if(coupon.getAmount()!=0 && !coupon.getEndDate().before(new Date())) {
        	coupon.setAmount(coupon.getAmount()-1);
			coupDB.addCouponPurchase(loggedCustomerId, coupon.getCouponId());
        }
    }


    public ArrayList<Coupon> getCustomerPurchaseHistory() throws SQLException, CustomerNotFoundException {
    	ArrayList<Coupon> coupons = coupDB.getAllCoupons();

    	for(Coupon coupon:coupons){
    		if(coupDB.getBuyerId(coupon.getCouponId()) != loggedCustomerId) coupons.remove(coupon);
		}

		return coupons;
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
