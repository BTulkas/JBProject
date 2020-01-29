package facades.exceptions;

import db.CompanyDBDAO;
import db.CouponDBDAO;
import db.CustomerDBDAO;

public abstract class ClientFacade {
	
	protected CompanyDBDAO compDB = new CompanyDBDAO();
	protected CouponDBDAO couponDB = new CouponDBDAO();
	protected CustomerDBDAO customDB = new CustomerDBDAO();

}
