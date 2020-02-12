package tests;

import beans.CategoryType;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import db.CouponDBDAO;
import db.exceptions.CompanyNotFoundException;
import db.exceptions.CustomerNotFoundException;
import facades.AdminFacade;
import facades.ClientFacade;
import facades.CompanyFacade;
import facades.exceptions.CompanyExistsException;
import facades.exceptions.CouponExists;
import facades.exceptions.CustomerExistsException;
import facades.exceptions.IncorrectPasswordException;
import login_manager.ClientType;
import login_manager.CouponExpirationDailyJob;
import login_manager.LoginManager;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;

public class testAll {
    public static void main(String[] args) throws CustomerExistsException, CouponExists{
        int testNum = 4;

        CouponExpirationDailyJob dailyJob = new CouponExpirationDailyJob();

        dailyJob.start();

        LoginManager loginManager = LoginManager.getInstance();

        try {
            AdminFacade loggedAs = (AdminFacade) loginManager.login("admin@admin.com", "admin", ClientType.Administrator);
            // Admin-Company methods
            //loggedAs.addCompany(new Company(String.format("Company_%s", testNum), "123", String.format("company@%s.com", testNum)));
            
			/*
			 * Company company_to_change = loggedAs.getOneCompany(2);
			 * 
			 * company_to_change.setPassword("12345");
			 * company_to_change.setName("Company2");
			 * 
			 * loggedAs.updateCompany(company_to_change);
			 */
            
            //System.out.println(loggedAs.getOneCompany(2));
            
            //loggedAs.deleteCompany(4);
            
             //System.out.println(Arrays.toString(loggedAs.getAllCompanies().toArray()));
            
            // Admin-Customer methods
            
            //loggedAs.addCustomer(new Customer("Not Nir", "Nir", "nir123", "nir@nir.nir"));
            
			/*
			 * Customer nir = loggedAs.getOneCustomer(1);
			 * 
			 * nir.setEmail("nir@nir.nir");
			 * 
			 * loggedAs.updateCustomer(nir);
			 * 
			 * loggedAs.deleteCustomer(3);
			 * 
			 * System.out.println(loggedAs.getAllCustomers());
			 */
            
            
            CompanyFacade loggedAsComp = (CompanyFacade) loginManager.login("some@one.com", "123456", ClientType.Administrator);
            
			/*
			 * loggedAsComp.addCoupon(new Coupon(1, CategoryType.Electronics,
			 * "Electro Boogaloo", "Discount on electro-stuff", Date.valueOf("2019-09-20"),
			 * Date.valueOf("2020-02-14"), 1, 10.5, "A Image"));
			 */
            
            CouponDBDAO coupDB = new CouponDBDAO();
            Coupon coup = coupDB.getOneCoupon(3);
            
            coup.setEndDate(Date.valueOf("2019-09-20"));
            
            loggedAsComp.updateCoupon(coup);
            
            
            System.out.println(loggedAsComp.getCompanyCoupons());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            dailyJob.quit();
        }

    }
}
