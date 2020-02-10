package tests;

import beans.Company;
import db.exceptions.CompanyNotFoundException;
import db.exceptions.CustomerNotFoundException;
import facades.AdminFacade;
import facades.ClientFacade;
import facades.exceptions.CompanyExistsException;
import facades.exceptions.IncorrectPasswordException;
import login_manager.ClientType;
import login_manager.CouponExpirationDailyJob;
import login_manager.LoginManager;

import java.sql.SQLException;
import java.util.Arrays;

public class testAll {
    public static void main(String[] args){
        int testNum = 4;

        CouponExpirationDailyJob dailyJob = new CouponExpirationDailyJob();

        dailyJob.start();

        LoginManager loginManager = LoginManager.getInstance();

        try {
            AdminFacade loggedAs = (AdminFacade) loginManager.login("admin@admin.com", "admin", ClientType.Administrator);

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
            
            loggedAs.deleteCompany(4);
            
            System.out.println(Arrays.toString(loggedAs.getAllCompanies().toArray()));
            
            

        } catch (SQLException e) {
            System.out.println(e);
        } catch (IncorrectPasswordException e) {
            System.out.println(e);
        } catch (CompanyNotFoundException e) {
            System.out.println(e);
        } catch (CustomerNotFoundException e) {
            System.out.println(e);
       // } catch (CompanyExistsException e) {
            System.out.println(e);
        } finally {
            dailyJob.quit();
        }

    }
}
