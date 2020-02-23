package facades;

import java.sql.SQLException;
import java.util.ArrayList;

import beans.Company;
import beans.Coupon;
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


	// Does what it says on the tin.
	@Override
	public boolean login(String email, String password) {
		if(email.equals("admin@admin.com") && password.equals("admin")) return true;
		else return false;
	}


	// All company methods.
	// Company getter methods

	// Returns an ArrayList of all companies.
	public ArrayList<Company> getAllCompanies() throws SQLException {
		return compDB.getAllCompanies();
	}


	// Returns one Company object by ID.
	public Company getOneCompany(int companyId) throws CompanyNotFoundException, SQLException {
		return compDB.getOneCompany(companyId);
	}


	// Company setter methods.


	// Adds a company.
	public void addCompany(Company company) throws SQLException, CompanyExistsException {

		// Iterates over all companies to throw exception if trying to add a company with existing email or name.
		for(Company comp : compDB.getAllCompanies()) {
			if(comp.getName().contentEquals(company.getName()) || comp.getEmail().contentEquals(company.getEmail())){
				throw new CompanyExistsException();
			}
		}

		compDB.addCompany(company);
	}


	// Updates company to match Company object given.
	public void updateCompany(Company company) throws CompanyNotFoundException, SQLException {

		compDB.updateCompany(company);

	}


	// Does what it says on the tin.
	public void deleteCompany(int companyId) throws SQLException, CustomerNotFoundException {

		// Gets and deletes all company coupons and purchases
		for(Coupon coupon:coupDB.getCompanyCoupons(companyId)){
			int coupID = coupon.getCouponId();
			coupDB.deleteCouponPurchase(coupID);
			coupDB.deleteCoupon(coupID);
		}

		// Delete company
		compDB.deleteCompany(companyId);
	}



	// All customer methods.
	// Customer getter methods


	// Returns ArrayList of all customers.
	public ArrayList<Customer> getAllCustomers() throws SQLException {
		return cusDB.getAllCustomers();
	}


	// Returns one Customer object by ID.
	public Customer getOneCustomer(int customerId) throws CustomerNotFoundException, SQLException {
		return cusDB.getOneCustomer(customerId);
	}



	// Customer setter methods.


	// Adds a customer.
	public void addCustomer(Customer customer) throws SQLException, CustomerExistsException {

		// Iterates over all Customers to throw exception if tying to add customer with existing email
		for(Customer cust : cusDB.getAllCustomers()) {
			if(cust.getEmail().contentEquals(customer.getEmail())){
				throw new CustomerExistsException();
			}
		}

		cusDB.addCustomer(customer);
	}


	// Updates customer to match Customer object given.
	public void updateCustomer(Customer customer) throws SQLException {

		cusDB.updateCustomer(customer);

	}


	// Does what it says on the tin.
	public void deleteCustomer(int customerId) throws SQLException {
		// Deletes all customer purchases for some reason before deleting customer
		coupDB.deleteCouponPurchaseByCustomer(customerId);

		cusDB.deleteCustomer(customerId);
	}

}
