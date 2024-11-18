package Models;

import java.sql.Date;

public class Customer {
    private int customerID;
    private String companyName;
    private String customerName;
    private String companyContact;
    private String billingAddress;
    private double amountPaid;
    private Date datePaid;

    public Customer(int customerID, String companyName, String customerName, String companyContact, String billingAddress, double amountPaid, Date datePaid) {
        this.customerID = customerID;
        this.companyName = companyName;
        this.customerName = customerName;
        this.companyContact = companyContact;
        this.billingAddress = billingAddress;
        this.amountPaid = amountPaid;
        this.datePaid = datePaid;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public Date getDatePaid() {
        return datePaid;
    }


    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public void setDatePaid(Date datePaid) {
        this.datePaid = datePaid;
    }
    
}
