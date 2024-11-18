package Controllers;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import Models.Schedule;
import Models.Driver;
import Models.Vehicle;
import Models.Request;
import Services.ScheduleDAO;
import Services.VehicleDAO;
import Services.DriverDAO; 
import Services.RequestDAO;

public class ScheduleController{
    private final ScheduleDAO dao;
    
    public ScheduleController() {
        dao = new ScheduleDAO();
    }

    public Object[][] getScheduleTableData() {
        List<Schedule> data;
        int dataRows;
        try {
            data = dao.getAllSchedules();
            dataRows = data.size();
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        Object[][] tableData = new Object[dataRows][5];
        
        for(int i = 0; i < data.size(); i++) {
            tableData[i] = new Object[] {
                data.get(i).getScheduleID(),
                data.get(i).getDate(),
                data.get(i).getDriverID(),
                data.get(i).getVehicleID(),
                data.get(i).getRequestID()
            };
        }

        return tableData;
    }

    public boolean createRecord(String date, String driverID, String vehicleID, String requestID) {
        try {
            int dbDriverID = Integer.parseInt(driverID);
            int dbVehicleID = Integer.parseInt(vehicleID);
            int dbRequestID = Integer.parseInt(requestID);
            Date dbDate = Date.valueOf(date);
            Schedule newRecord = new Schedule(0, dbDate, dbDriverID, dbVehicleID, dbRequestID);
            dao.addSchedule(newRecord);
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    public boolean deleteRecord(int scheduleID) {
        try {
            dao.deleteSchedule(scheduleID);
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    public List<Driver> getAvailableDrivers() {
        try {
            DriverDAO driverDAO = new DriverDAO();
            return driverDAO.getAllAvailableDrivers();
        } catch (SQLException e) {
            System.err.println(e);
            return Collections.emptyList();
        }
    }

    public List<Vehicle> getAvailableVehicles() {
        try {
            VehicleDAO vehicleDAO = new VehicleDAO();
            return vehicleDAO.getAllAvailableVehicles();
        } catch (SQLException e) {
            System.err.println(e);
            return Collections.emptyList();
        }
    }
}