package Models;

public class Driver {
    private int driverID;
    private String lastname;
    private String firstname;
    private double rate;
    private String contactNumber;
    private Status status;

    public enum Status {
        AVAILABLE,
        IN_TRANSIT,
        ON_LEAVE,
        UNAVAILABLE
    }

    public Driver(int driverID, String lastname, String firstname, double rate, String contactNumber, Status status) {
        this.driverID = driverID;
        this.lastname = lastname;
        this.firstname = firstname;
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
   }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
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
