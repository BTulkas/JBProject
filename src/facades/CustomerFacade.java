package facades;

import beans.Coupon;
import beans.Customer;
import db.CouponDBDAO;
import db.CustomerDBDAO;
import db.exceptions.CustomerNotFoundException;
import facades.exceptions.IncorrectPasswordException;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerFacade extends ClientFacade {

    @Override
    public boolean login(String email, String password) throws SQLException, CustomerNotFoundException, IncorrectPasswordException {
        ArrayList<Customer> companies = customDB.getAllCustomers();

        for (Customer comp : companies) {
            if (comp.getEmail() == email) {
                if (comp.getPassword() == password) break;
                else throw new IncorrectPasswordException();
            }
        }

        if(!customDB.isCustomerExists(email, password)) throw new CustomerNotFoundException();
        else return true;
    }


    public void addCoupon(Coupon coupon) throws SQLException {
        Coupon coupon_to_buy = coupDB.getOneCoupon(coupon.getCouponId());

        if(coupon_to_buy.get)
    }
}
