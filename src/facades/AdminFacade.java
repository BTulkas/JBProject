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
	public void updateCompanyEmail(int companyId, String newEmail) throws CompanyNotFoundException, SQLException {

		Company comp_to_update = compDB.getOneCompany(companyId);

		comp_to_update.setEmail(newEmail);

		compDB.updateCompany(comp_to_update);

	}


	public void updateCompanyPassword(int companyId, String newPassword) throws CompanyNotFoundException, SQLException {

		Company comp_to_update = compDB.getOneCompany(companyId);

		comp_to_update.setPassword(newPassword);

		compDB.updateCompany(comp_to_update);

	}
	
	public void deleteCompany(int id) throws SQLException {
		compDB.deleteCompany(id);
	}



	// All customer methods.
	// Customer getter methods
	public ArrayList<Customer> getAllCustomers() throws SQLException {
		return cusDB.getAllCustomers();
	}

	public Customer getOneCustomer(int customerId) throws CustomerNotFoundException, SQLException {
		return cusDB.getOneCustomer(customerId);
	}


	public void addCompany(Customer customer) throws SQLException, CustomerExistsException {

		ArrayList<Company> companies = compDB.getAllCompanies();
		for(Company cust : companies) {
			if(cust.getEmail().contentEquals(customer.getEmail())){
				throw new CustomerExistsException();
			}
		}

		cusDB.addCustomer(customer);
	}

	// Customer setter methods.
	public void updateCustomerEmail(int customerId, String newEmail) throws CustomerNotFoundException, SQLException {

		Customer cus_to_update = cusDB.getOneCustomer(customerId);

		cus_to_update.setEmail(newEmail);

		cusDB.updateCustomer(cus_to_update);

	}


	public void updateCustomerPassword(int customerId, String newPassword) throws CustomerNotFoundException, SQLException {

		Customer customer_to_update = cusDB.getOneCustomer(customerId);

		customer_to_update.setPassword(newPassword);

		cusDB.updateCustomer(customer_to_update);

	}


	public void updateCustomerFirstName(int customerId, String newName) throws CustomerNotFoundException, SQLException {

		Customer customer_to_update = cusDB.getOneCustomer(customerId);

		customer_to_update.setFirstName(newName);

		cusDB.updateCustomer(customer_to_update);

	}


	public void updateCustomerLastName(int customerId, String newName) throws CustomerNotFoundException, SQLException {

		Customer customer_to_update = cusDB.getOneCustomer(customerId);

		customer_to_update.setLastName(newName);

		cusDB.updateCustomer(customer_to_update);

	}

	public void deleteCustomer(int id) throws SQLException {
		cusDB.deleteCustomer(id);
	}

}
