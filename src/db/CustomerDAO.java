package db;

import beans.Customer;
import db.exceptions.CustomerNotFoundException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO {

    int isCustomerExists(String email) throws SQLException;
    void addCustomer(Customer customer) throws SQLException;
    void updateCustomer(Customer customer) throws SQLException;
    void deleteCustomer(int customerId) throws SQLException;
    ArrayList<Customer> getAllCustomers() throws SQLException;
    Customer getOneCustomer(int customerId) throws CustomerNotFoundException, SQLException;

}
