import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Models.Customer;

public class CustomerDAO {
    private Connection connection;

    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    public void addCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO customers (customerID, companyName, customerName, companyContact, billingAddress, amountPaid, datePaid) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, customer.getCustomerID());
            stmt.setString(2, customer.getCompanyName());
            stmt.setString(3, customer.getCustomerName());
            stmt.setString(4, customer.getCompanyContact());
            stmt.setString(5, customer.getBillingAddress());
            stmt.setDouble(6, customer.getAmountPaid());
            stmt.setDate(7, new java.sql.Date(customer.getDatePaid().getTime()));
            stmt.executeUpdate();
        }
    }

    public Customer getCustomer(int customerID) throws SQLException {
        String query = "SELECT * FROM customers WHERE customerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, customerID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                        rs.getInt("customerID"),
                        rs.getString("companyName"),
                        rs.getString("customerName"),
                        rs.getString("companyContact"),
                        rs.getString("billingAddress"),
                        rs.getDouble("amountPaid"),
                        rs.getDate("datePaid")
                    );
                }
            }
        }
        return null;
    }

    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                customers.add(new Customer(
                    rs.getInt("customerID"),
                    rs.getString("companyName"),
                    rs.getString("customerName"),
                    rs.getString("companyContact"),
                    rs.getString("billingAddress"),
                    rs.getDouble("amountPaid"),
                    rs.getDate("datePaid")
                ));
            }
        }
        return customers;
    }

    public void updateCustomer(Customer customer) throws SQLException {
        String query = "UPDATE customers SET companyName = ?, customerName = ?, companyContact = ?, billingAddress = ?, amountPaid = ?, datePaid = ? WHERE customerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, customer.getCompanyName());
            stmt.setString(2, customer.getCustomerName());
            stmt.setString(3, customer.getCompanyContact());
            stmt.setString(4, customer.getBillingAddress());
            stmt.setDouble(5, customer.getAmountPaid());
            stmt.setDate(6, new java.sql.Date(customer.getDatePaid().getTime()));
            stmt.setInt(7, customer.getCustomerID());
            stmt.executeUpdate();
        }
    }

    public void deleteCustomer(int customerID) throws SQLException {
        String query = "DELETE FROM customers WHERE customerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, customerID);
            stmt.executeUpdate();
        }
    }
}
