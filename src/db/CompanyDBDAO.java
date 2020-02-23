package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Company;
import db.exceptions.CompanyNotFoundException;

public class CompanyDBDAO implements CompanyDAO {
	
	private ConnectionPool pool = ConnectionPool.getInstance();



	// Returns the ID of a company with matching email or 0 if none match.
	@Override
	public int isCompanyExists(String email) throws SQLException {
		Connection con = pool.getConnection();

		try {

			PreparedStatement stmnt = con.prepareStatement("SELECT * FROM companies WHERE email = ?");
			stmnt.setString(1, email);
			ResultSet rs = stmnt.executeQuery();

			if(rs.next()) return rs.getInt("company_id");
			else return 0;

		}finally {
			pool.restoreConnection(con);
		}
	}


	// Saves Company object to DB, must be created without ID.
	@Override
	public void addCompany(Company company) throws SQLException{
		Connection con = pool.getConnection();
		
		try {
		PreparedStatement stmnt = con.prepareStatement("INSERT INTO companies (name, password, email) VALUES(?, ?, ?)");
		stmnt.setString(1, company.getName());
		stmnt.setString(2, company.getPassword());
		stmnt.setString(3, company.getEmail());
		
		stmnt.execute();

		} finally {
		pool.restoreConnection(con);	
		}
	}


	// Updates DB to match Company object created with ID through getOneCompany method.
	@Override
	public void updateCompany(Company company) throws SQLException {
		Connection con = pool.getConnection();
		
		try {
		PreparedStatement stmnt = con.prepareStatement("UPDATE companies SET name = ?, password = ?, email = ? where company_id = ?");
		stmnt.setString(1, company.getName());
		stmnt.setString(2, company.getPassword());
		stmnt.setString(3, company.getEmail());
		stmnt.setInt(4, company.getCompanyId());
		
		stmnt.execute();
		
		} finally {
		pool.restoreConnection(con);	
		}
	}


	// Removes a Company from the DB by ID
	@Override
	public void deleteCompany(int companyId) throws SQLException {
		Connection con = pool.getConnection();

		try {
			PreparedStatement stmnt = con.prepareStatement("DELETE FROM companies WHERE company_id = ?");

			stmnt.setInt(1, companyId);

			stmnt.execute();

		} finally {
			pool.restoreConnection(con);
		}

	}


	// Gets an ArrayList of Companies from all companies in the DB
	@Override
	public ArrayList<Company> getAllCompanies() throws SQLException {
		// Generates an empty list.
		ArrayList<Company> companies = new ArrayList<Company>(0);
		Connection con = pool.getConnection();
		
		try {
			// Gets all of the companies in the DB
			PreparedStatement stmnt = con.prepareStatement("SELECT * FROM companies");
			ResultSet rs = stmnt.executeQuery();
			// Creates a java object from each row of data and adds it the list.
			while(rs.next()) {
				companies.add(new Company(rs.getInt("company_id"), rs.getString("name"), rs.getString("password"), rs.getString("email")));
			}
			return companies;
			
		}finally {
			pool.restoreConnection(con);
		}
		
	}


	// Returns a Company matching a DB row in companies by ID
	@Override
	public Company getOneCompany(int companyId) throws SQLException, CompanyNotFoundException {
		Connection con = pool.getConnection();
		
		try {
			
			PreparedStatement stmnt = con.prepareStatement("SELECT * FROM companies where company_id = ?");
			stmnt.setInt(1, companyId);
			ResultSet rs = stmnt.executeQuery();
			if(rs.next()) return new Company(rs.getInt("company_id"), rs.getString("name"), rs.getString("password"), rs.getString("email"));
			else throw new CompanyNotFoundException();
			
		}finally {
			pool.restoreConnection(con);
		}
	}

}
