package facades;

import java.sql.SQLException;
import java.util.ArrayList;

import beans.Company;
import db.CompanyDBDAO;
import facades.exceptions.CompanyExistsException;

public class AdminFacade  extends ClientFacade{
	
	private CompanyDBDAO compDB = new CompanyDBDAO();
	
	public void addCompany(Company company) throws SQLException, CompanyExistsException {
		
		ArrayList<Company> companies = compDB.getAllCompanies();
		for(Company comp : companies) {
			if(comp.getName().contentEquals(company.getName()) || comp.getEmail().contentEquals(company.getEmail())){
				throw new CompanyExistsException();
			}
		}
		
		compDB.addCompany(company);
	}
	
	public void deleteCompany(int id) throws SQLException {
		compDB.deleteCompany(id);
	}

}
