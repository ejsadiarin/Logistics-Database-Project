package Models;

import java.sql.Date;

public class Request {
    private int requestID;
    private Date requestedDate; // use java.sql.Date for DATETIME
    private String product;
    private String origin;
    private String destination;
    private double loadWeight;
    private int customerID;

    public Request(int requestID, Date requestedDate, String product, String origin, String destination, double loadWeight, int customerID) {
        this.requestID = requestID;
        this.requestedDate = requestedDate;
        this.product = product;
        this.origin = origin;
        this.destination = destination;
        this.loadWeight = loadWeight;
        this.customerID = customerID;
    }

    // Getters and Setters
    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public Date getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(Date requestedDate) {
        this.requestedDate = requestedDate;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getLoadWeight() {
        return loadWeight;
    }

    public void setLoadWeight(double loadWeight) {
        this.loadWeight = loadWeight;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
}
