package Models;

public class Logistics {
    private int logisticsID;
    private double distance;
    private double normalCost;
    private Status status;
    private int scheduleID;

    public enum Status {
        ARRIVED,
        IN_TRANSIT,
        CANCELLED,
        PENDING
    }

    public Logistics(int logisticsID, double distance, double normalCost, Status status, int scheduleID) {
        this.logisticsID = logisticsID;
        this.distance = distance;
        this.normalCost = normalCost;
        this.status = status;
        this.scheduleID = scheduleID;
    }


    public int getLogisticsID() {
        return logisticsID;
    }

    public void setLogisticsID(int logisticsID) {
        this.logisticsID = logisticsID;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getNormalCost() {
        return normalCost;
    }

    public void setNormalCost(double normalCost) {
        this.normalCost = normalCost;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

}
