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
	


	@Override
	public int isCompanyExists(String email, String password) throws SQLException {
		Connection con = pool.getConnection();

		try {

			PreparedStatement stmnt = con.prepareStatement("SELECT * FROM companies WHERE email = ? and password = ?");
			stmnt.setString(1, email);
			stmnt.setString(2, password);
			ResultSet rs = stmnt.executeQuery();

			if(rs.next()) return rs.getInt("company_id");
			else return 0;

		}finally {
			pool.restoreConnection(con);
		}
	}

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

	@Override
	public ArrayList<Company> getAllCompanies() throws SQLException {
		ArrayList<Company> companies = new ArrayList<Company>(0);
		Connection con = pool.getConnection();
		
		try {
			
			PreparedStatement stmnt = con.prepareStatement("SELECT * FROM companies");
			ResultSet rs = stmnt.executeQuery();
			while(rs.next()) {
				companies.add(new Company(rs.getInt("company_id"), rs.getString("name"), rs.getString("password"), rs.getString("email")));
			}
			return companies;
			
		}finally {
			pool.restoreConnection(con);
		}
		
	}

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
