package Services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Database.DatabaseConnection;
import Models.Customer;

public class CustomerDAO {
    private Connection connection;

    public CustomerDAO() {
    }

    private Connection getConnection() throws SQLException {
        if (this.connection == null || this.connection.isClosed()) {
            this.connection = DatabaseConnection.getConnection();
        }
        return this.connection;
    }

    public void addCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO customers (customer_id, company_name, customer_name, company_contact, billing_address, amount_paid, date_paid) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, customer.getCustomerID());
            stmt.setString(2, customer.getCompanyName());
            stmt.setString(3, customer.getCustomerName());
            stmt.setString(4, customer.getCompanyContact());
            stmt.setString(5, customer.getBillingAddress());
            stmt.setDouble(6, customer.getAmountPaid());
            stmt.setDate(7, customer.getDatePaid()); // Use java.sql.Date
            stmt.executeUpdate();
        }
    }

    public Customer getCustomer(int customerID) throws SQLException {
        String query = "SELECT * FROM customers WHERE customer_id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, customerID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("company_name"),
                        rs.getString("customer_name"),
                        rs.getString("company_contact"),
                        rs.getString("billing_address"),
                        rs.getDouble("amount_paid"),
                        rs.getDate("date_paid") // Retrieve as java.sql.Date
                    );
                }
            }
        }
        return null;
    }

    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                customers.add(new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("company_name"),
                    rs.getString("customer_name"),
                    rs.getString("company_contact"),
                    rs.getString("billing_address"),
                    rs.getDouble("amount_paid"),
                    rs.getDate("date_paid") // Retrieve as java.sql.Date
                ));
            }
        }
        return customers;
    }

    public void updateCustomer(Customer customer) throws SQLException {
        String query = "UPDATE customers SET company_name = ?, customer_name = ?, company_contact = ?, billing_address = ?, amount_paid = ?, date_paid = ? WHERE customer_id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setString(1, customer.getCompanyName());
            stmt.setString(2, customer.getCustomerName());
            stmt.setString(3, customer.getCompanyContact());
            stmt.setString(4, customer.getBillingAddress());
            stmt.setDouble(5, customer.getAmountPaid());
            stmt.setDate(6, customer.getDatePaid()); // Use java.sql.Date
            stmt.setInt(7, customer.getCustomerID());
            stmt.executeUpdate();
        }
    }

    public void deleteCustomer(int customerID) throws SQLException {
        String query = "DELETE FROM customers WHERE customer_id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, customerID);
            stmt.executeUpdate();
        }
    }
}
