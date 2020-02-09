package login_manager;

import beans.Coupon;
import db.CouponDBDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class CouponExpirationDailyJob extends Thread {

    CouponDBDAO coupDB = new CouponDBDAO();
    private boolean keepGoing = true;


    public void setKeepGoing(boolean keepGoing) {
        this.keepGoing = keepGoing;
    }


    @Override
    public void run(){

        while(keepGoing == true){

            try {
                ArrayList<Coupon> coupons = coupDB.getAllCoupons();

                for(Coupon coupon:coupons){
                    if(coupon.getEndDate().after(new Date())){
                        coupDB.deleteCoupon(coupon.getCouponId());
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
               Thread.sleep(86400000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void quit(){
        setKeepGoing(false);
        this.interrupt();
    }

}
