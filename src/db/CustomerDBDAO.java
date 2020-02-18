package db;

import beans.Customer;
import db.exceptions.CustomerNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDBDAO implements CustomerDAO{

    private ConnectionPool pool = ConnectionPool.getInstance();


    @Override
    public int isCustomerExists(String email, String password) throws SQLException {
        Connection con = pool.getConnection();

        try {

            PreparedStatement stmnt = con.prepareStatement("SELECT * FROM customers WHERE email = ? and password = ?");
            stmnt.setString(1, email);
            stmnt.setString(2, password);
            ResultSet rs = stmnt.executeQuery();

            if(rs.next()) return rs.getInt("customer_id");
            else return 0;

        }finally {
            pool.restoreConnection(con);
        }
    }

    @Override
    public void addCustomer(Customer customer) throws SQLException {
        Connection con = pool.getConnection();

        try {
            PreparedStatement stmnt = con.prepareStatement("INSERT INTO customers (first_name, last_name, email, password) VALUES(?, ?, ?, ?)");
            stmnt.setString(1, customer.getFirstName());
            stmnt.setString(2, customer.getLastName());
            stmnt.setString(3, customer.getEmail());
            stmnt.setString(4, customer.getPassword());

            stmnt.execute();

        } finally {
            pool.restoreConnection(con);
        }

    }

    @Override
    public void updateCustomer(Customer customer) throws SQLException {
        Connection con = pool.getConnection();

        try {
            PreparedStatement stmnt = con.prepareStatement("UPDATE customers SET first_name = ?, last_name = ?, password = ?, email = ? where customer_id = ?");
            stmnt.setString(1, customer.getFirstName());
            stmnt.setString(2, customer.getLastName());
            stmnt.setString(3, customer.getPassword());
            stmnt.setString(4, customer.getEmail());
            stmnt.setInt(5, customer.getCustomerId());

            stmnt.execute();

        } finally {
            pool.restoreConnection(con);
        }

    }

    @Override
    public void deleteCustomer(int customerId) throws SQLException {
        Connection con = pool.getConnection();

        try {
            PreparedStatement stmnt = con.prepareStatement("DELETE FROM customers WHERE customer_id = ?");

            stmnt.setInt(1, customerId);

            stmnt.execute();

        } finally {
            pool.restoreConnection(con);
        }

    }

    @Override
    public ArrayList<Customer> getAllCustomers() throws SQLException {
        ArrayList<Customer> customers = new ArrayList<Customer>(0);
        Connection con = pool.getConnection();

        try {

            PreparedStatement stmnt = con.prepareStatement("SELECT * FROM customers");
            ResultSet rs = stmnt.executeQuery();
            while(rs.next()) {
                customers.add(new Customer(rs.getInt("customer_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("password")));
            }
            return customers;

        }finally {
            pool.restoreConnection(con);
        }
    }

    @Override
    public Customer getOneCustomer(int customerId) throws CustomerNotFoundException, SQLException {
        Connection con = pool.getConnection();

        try {

            PreparedStatement stmnt = con.prepareStatement("SELECT * FROM customers WHERE customer_id = ?");
            stmnt.setInt(1, customerId);
            ResultSet rs = stmnt.executeQuery();
            if(rs.next()) return new Customer(rs.getInt("customer_id"), rs.getString("first_name"),  rs.getString("last_name"), rs.getString("email"), rs.getString("password"));
            else throw new CustomerNotFoundException();

        }finally {
            pool.restoreConnection(con);
        }
    }

}
