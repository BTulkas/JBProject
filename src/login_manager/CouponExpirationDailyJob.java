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

        do{

            try {
                ArrayList<Coupon> coupons = coupDB.getAllCoupons();

                for(Coupon coupon:coupons){
                    if(coupon.getEndDate().before(new Date())){
                        coupDB.deleteCoupon(coupon.getCouponId());
                    }
                }
            } catch (SQLException e) {
                System.out.println(e);
            }

            try {
               Thread.sleep(86400000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }while(keepGoing == true);
    }

    public void quit(){
        setKeepGoing(false);
        this.interrupt();
    }

}
