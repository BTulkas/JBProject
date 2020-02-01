package tests;

import java.sql.SQLException;

import beans.Company;
import db.CompanyDBDAO;
import db.ConnectionPool;
import db.exceptions.CompanyNotFoundException;

public class TestCompany {

	public static void main(String[] args) {

		CompanyDBDAO compDB = new CompanyDBDAO();
		
		try {
			// compDB.addCompany(new Company("Castro", "123456", "some@one.com"));
			
			System.out.println(compDB.getAllCompanies());
			
		} catch (SQLException e) {
			System.out.println("Add failed! " + e.getMessage());
		}
		
		try {
			System.out.println(compDB.getOneCompany(1));
			System.out.println(compDB.getOneCompany(2));
		} catch (SQLException | CompanyNotFoundException e) {
			System.out.println("Error! " + e.getMessage());
		}
		
		ConnectionPool.getInstance().CloseAllConnections();

	}

}
