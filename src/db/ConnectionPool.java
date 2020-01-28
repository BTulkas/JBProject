package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectionPool {
	
	private static ConnectionPool instance = new ConnectionPool();
	private ArrayList<Connection> connections = new ArrayList<Connection>();
	private static final int MAX_CONNECTIONS = 10;
	
	
	private ConnectionPool() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			for(int i = 1; i <= MAX_CONNECTIONS; i++) {
				connections.add(DriverManager.getConnection("jdbc:mysql://localhost:3306/project1?serverTimezone=UTC", "root", "1234"));
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Can't find class! " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Error connectinv to db " + e.getMessage());
		} 
		
	}
	
	public static ConnectionPool getInstance() {
		return instance;
	}
	
	public synchronized Connection getConnection() {
		
		while(connections.size() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		
		Connection con = connections.get(0);
		connections.remove(0);
		return con;
	}
	
	public synchronized void restoreConnection(Connection connection) {
		connections.add(connection);
		notify(); // wakes up the waiting thread.
	}
	
	public void CloseAllConnections() {
		for(Connection con : connections) {
			try {
				con.close();
			}catch(SQLException e) {}
		}
	}

}
