package login_manager;

import beans.Coupon;
import db.CouponDBDAO;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class CouponExpirationDailyJob extends Thread {

    CouponDBDAO coupDB = new CouponDBDAO();
    private boolean keepGoing = true;


    public void setKeepGoing(boolean keepGoing) {
        this.keepGoing = keepGoing;
    }


    // Iterates over all coupons and deletes all expired coupons and their purchases.
    @Override
    public void run(){

        do{

            try {
            	// Gets a correct date to pass to Date, because sql Date is the IE of Dates.
            	Calendar cal = Calendar.getInstance(); 

                for(Coupon coupon:coupDB.getAllCoupons()){
                    if(coupon.getEndDate().before(new Date(cal.getTimeInMillis()))){
                        coupDB.deleteCouponPurchase(coupon.getCouponId());
                        coupDB.deleteCoupon(coupon.getCouponId());
                    }
                }
            } catch (SQLException e) {
                System.out.println(e);
            }

            try {
               Thread.sleep(86400000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

        }while(keepGoing == true);
    }


    // Method to stop the thread when closing the program.
    public void quit(){
        setKeepGoing(false);
        this.interrupt();
    }

}
