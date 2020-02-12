package facades;

import java.sql.SQLException;
import java.util.ArrayList;

import beans.Company;
import beans.Customer;
import db.CompanyDBDAO;
import db.CustomerDBDAO;
import db.exceptions.CompanyNotFoundException;
import db.exceptions.CustomerNotFoundException;
import facades.exceptions.CompanyExistsException;
import facades.exceptions.CustomerExistsException;

public class AdminFacade  extends ClientFacade{
	
	private CompanyDBDAO compDB = new CompanyDBDAO();
	private CustomerDBDAO cusDB = new CustomerDBDAO();


	@Override
	public boolean login(String email, String password) {
		if(email == "admin@admin.com" && password == "admin") return true;
		else return false;
	}


	// All company methods.
	// Company getter methods
	public ArrayList<Company> getAllCompanies() throws SQLException {
		return compDB.getAllCompanies();
	}

	public Company getOneCompany(int companyId) throws CompanyNotFoundException, SQLException {
		return compDB.getOneCompany(companyId);
	}


	public void addCompany(Company company) throws SQLException, CompanyExistsException {
		
		ArrayList<Company> companies = compDB.getAllCompanies();
		for(Company comp : companies) {
			if(comp.getName().contentEquals(company.getName()) || comp.getEmail().contentEquals(company.getEmail())){
				throw new CompanyExistsException();
			}
		}
		
		compDB.addCompany(company);
	}

	// Company setter methods.
	public void updateCompany(Company company) throws CompanyNotFoundException, SQLException {

		compDB.updateCompany(company);

	}


	
	public void deleteCompany(int companyId) throws SQLException {
		compDB.deleteCompany(companyId);
	}



	// All customer methods.
	// Customer getter methods
	public ArrayList<Customer> getAllCustomers() throws SQLException {
		return cusDB.getAllCustomers();
	}

	public Customer getOneCustomer(int customerId) throws CustomerNotFoundException, SQLException {
		return cusDB.getOneCustomer(customerId);
	}



	// Customer setter methods.
	public void addCustomer(Customer customer) throws SQLException, CustomerExistsException {

		ArrayList<Company> companies = compDB.getAllCompanies();
		for(Company cust : companies) {
			if(cust.getEmail().contentEquals(customer.getEmail())){
				throw new CustomerExistsException();
			}
		}

		cusDB.addCustomer(customer);
	}


	public void updateCustomer(Customer customer) throws CustomerNotFoundException, SQLException {

		cusDB.updateCustomer(customer);

	}


	public void deleteCustomer(int id) throws SQLException {
		cusDB.deleteCustomer(id);
	}

}
