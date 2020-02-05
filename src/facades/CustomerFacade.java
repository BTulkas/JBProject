package facades;

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


    public void purchaseCoupon(Coupon coupon) throws SQLException {

        if(coupon.getAmount()==0) {
        	
        } else if(coupon.getEndDate().before(new Date())){
        	
        } else {
        	coupon.setAmount(coupon.getAmount()-1);
        	coupDB.addCouponPurchase(loggedCustomerId, coupon.getCouponId());
        }
    }
}
