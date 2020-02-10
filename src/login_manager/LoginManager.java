package login_manager;

import beans.Customer;
import db.exceptions.CompanyNotFoundException;
import db.exceptions.CustomerNotFoundException;
import facades.AdminFacade;
import facades.ClientFacade;
import facades.CompanyFacade;
import facades.CustomerFacade;
import facades.exceptions.IncorrectPasswordException;

import java.sql.SQLException;

public class LoginManager {

    private static LoginManager instance = new LoginManager();


    private LoginManager(){
    }


    public static LoginManager getInstance(){
        return instance;
    }


    public ClientFacade login(String email, String password, ClientType clientType) throws SQLException, IncorrectPasswordException, CompanyNotFoundException, CustomerNotFoundException {

        switch(clientType){
            case Administrator:
                AdminFacade adminFacade = new AdminFacade();
                if(adminFacade.login(email, password)){
                    return adminFacade;
                }
            case Company:
                CompanyFacade companyFacade = new CompanyFacade();
                if(companyFacade.login(email, password)){
                    return companyFacade;
                }
            case Customer:
                CustomerFacade customerFacade = new CustomerFacade();
                if(customerFacade.login(email, password)){
                    return customerFacade;
                }
            default:
                    return null;

            }
    }

}
