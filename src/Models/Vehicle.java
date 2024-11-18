package Models;

import java.sql.Date;

public class Vehicle {
    private int vehicleID;
    private String plateNumber;
    private double fuelEconomy;
    private Date lastMaintenanceDate;
    private double maxLoadWeight;
    private Status status;

    public enum Status {
        AVAIALABLE,
        IN_TRANSIT,
        UNAVAILABLE,
        NEEDS_MAINTENANCE
    }

    public Vehicle(int vehicleID, String plateNumber, double fuelEconomy, Date lastMaintenanceDate, double maxLoadWeight, Status status) {
        this.vehicleID = vehicleID;
        this.plateNumber = plateNumber;
        this.fuelEconomy = fuelEconomy;
        this.lastMaintenanceDate = lastMaintenanceDate;
        this.maxLoadWeight = maxLoadWeight;
        this.status = status;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public double getFuelEconomy() {
        return fuelEconomy;
    }

    public void setFuelEconomy(double fuelEconomy) {
        this.fuelEconomy = fuelEconomy;
    }

    public Date getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }

    public void setLastMaintenanceDate(Date lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    public double getMaxLoadWeight() {
        return maxLoadWeight;
    }

    public void setMaxLoadWeight(double maxLoadWeight) {
        this.maxLoadWeight = maxLoadWeight;
    }

    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
}
