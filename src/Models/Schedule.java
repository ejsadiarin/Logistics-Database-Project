package Models;

import java.util.Date;

public class Schedule {
    private int scheduleID;
    private Date date;
    private int driverID;
    private int vehicleID;
    private int requestID;

    public Schedule(int scheduleID, Date date, int driverID, int vehicleID, int requestID) {
        this.scheduleID = scheduleID;
        this.date = date;
        this.driverID = driverID;
        this.vehicleID = vehicleID;
        this.requestID = requestID;
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDriverID() {
        return driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }
}
