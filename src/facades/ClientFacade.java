package facades;

import db.CompanyDBDAO;
import db.CouponDBDAO;
import db.CustomerDBDAO;
import db.exceptions.CompanyNotFoundException;
import facades.exceptions.IncorrectPasswordException;

import java.sql.SQLException;

public abstract class ClientFacade {
	
	protected CompanyDBDAO compDB = new CompanyDBDAO();
	protected CouponDBDAO couponDB = new CouponDBDAO();
	protected CustomerDBDAO customDB = new CustomerDBDAO();

	public abstract boolean login(String email, String password) throws SQLException, CompanyNotFoundException, IncorrectPasswordException;

}
