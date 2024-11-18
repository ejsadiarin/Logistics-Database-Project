package Controllers;

import Services.CustomerDAO;
import Models.Customer;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class CustomerController {
    private final CustomerDAO dao;

    public CustomerController() {
        dao = new CustomerDAO();
    }

    public Object[][] getCustomersTableData() {
        List<Customer> data;
        int dataRows;
        try {
            data = dao.getAllCustomers();
            dataRows = data.size();
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }

        Object[][] tableData = new Object[dataRows][7]; // Adjusted for 7 columns
        for (int i = 0; i < data.size(); i++) {
            tableData[i] = new Object[]{
                data.get(i).getCustomerID(),
                data.get(i).getCompanyName(),
                data.get(i).getCustomerName(),
                data.get(i).getCompanyContact(),
                data.get(i).getBillingAddress(),
                data.get(i).getAmountPaid(),
                data.get(i).getDatePaid()
            };
        }

        return tableData;
    }

    public boolean createRecord(String companyName, String customerName, String companyContact, String billingAddress, String amountPaid, Date datePaid) {
        try {
            Double amountPaidDouble = Double.parseDouble(amountPaid);
            Customer newRecord = new Customer(0, companyName, customerName, companyContact, billingAddress, amountPaidDouble, datePaid);
            dao.addCustomer(newRecord);
            return true;
        } catch (SQLException e) {
            System.err.println("Error creating customer: " + e.getMessage());
            return false;
        }
    }

    public boolean updateRecord(int customerID, String companyName, String customerName, String companyContact, String billingAddress, String amountPaid, Date datePaid) {
        try {
            Double amountPaidDouble = Double.parseDouble(amountPaid);
            Customer updatedRecord = new Customer(customerID, companyName, customerName, companyContact, billingAddress, amountPaidDouble, datePaid);
            dao.updateCustomer(updatedRecord);
            return true;
        } catch (SQLException e) {
            System.err.println("Error updating customer: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteRecord(int customerID) {
        try {
            dao.deleteCustomer(customerID);
            return true;
        } catch (SQLException e) {
            System.err.println("Error deleting customer: " + e.getMessage());
            return false;
        }
    }
}
