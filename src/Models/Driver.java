package Models;

public class Driver {
    private int driverID;
    private String fullName;
    private double rate;
    private String contactNumber;
    private Status status;

    public enum Status {
        AVAILABLE,
        IN_TRANSIT,
        ON_LEAVE,
        UNAVAILABLE
    }

    public Driver(int driverID, String fullName, double rate, String contactNumber, Status status) {
        this.driverID = driverID;
        this.fullName = fullName;
        this.rate = rate;
        this.contactNumber = contactNumber;
        this.status = status;
    }

    public int getDriverID() {
        return driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


}
