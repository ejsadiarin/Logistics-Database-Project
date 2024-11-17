package Models;

import java.sql.Timestamp;

public class Schedule {
    private int scheduleID;
    private Timestamp date;
    private int driverID;
    private int vehicleID;
    private int requestID;

    public Schedule(int scheduleID, Timestamp date, int driverID, int vehicleID, int requestID) {
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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
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
