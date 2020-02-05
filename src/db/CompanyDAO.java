package db;

import java.sql.SQLException;
import java.util.ArrayList;

import beans.Company;
import db.exceptions.CompanyNotFoundException;

public interface CompanyDAO {
	
	int isCompanyExists(String email, String password) throws SQLException;
	void addCompany(Company company) throws SQLException;
	void updateCompany(Company company) throws SQLException;
	void deleteCompany(int companyId) throws SQLException;
	ArrayList<Company> getAllCompanies() throws SQLException;
	Company getOneCompany(int companyId) throws SQLException, CompanyNotFoundException;
	
}
