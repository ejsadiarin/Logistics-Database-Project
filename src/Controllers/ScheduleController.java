package Controllers;

import Models.Driver;
import Models.Request;
import Models.Schedule;
import Models.Vehicle;
import Services.DriverDAO;
import Services.RequestDAO;
import Services.ScheduleDAO;
import Services.VehicleDAO;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

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

    public boolean createRecord(String date, int driverID, int vehicleID, int requestID) {
        try {
            Timestamp dbDate = Timestamp.valueOf(date);
            Schedule newRecord = new Schedule(0, dbDate, driverID, vehicleID, requestID);
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

    public Object[][] getRequestID() {
        List<Request> data;
        int dataRows;
        try {
            RequestDAO requestDAO = new RequestDAO();
            data = requestDAO.getAllRequests(); // TODO: Get all requests without schedules
            dataRows = data.size();
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }

        Object[][] tableData = new Object[dataRows][1];
        for (int i = 0; i < data.size(); i++) {
            tableData[i] = new Object[]{
                data.get(i).getRequestID()
            };
        }
        return tableData;
    }

    public Object[][] getAvailableDrivers() {
        List<Driver> data;
        int dataRows;
        try {
            DriverDAO driverDAO = new DriverDAO();
            data = driverDAO.getAllAvailableDrivers();
            dataRows = data.size();
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }

        Object[][] tableData = new Object[dataRows][1];
        for (int i = 0; i < data.size(); i++) {
            tableData[i] = new Object[]{
                data.get(i).getDriverID()
            };
        }
        return tableData;
    }

    public Object[][] getAvailableVehicles() {
        List<Vehicle> data;
        int dataRows;
        try {
            VehicleDAO vehicleDAO = new VehicleDAO();
            data = vehicleDAO.getAllAvailableVehicles();
            dataRows = data.size();
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }

        Object[][] tableData = new Object[dataRows][1];
        for (int i = 0; i < data.size(); i++) {
            tableData[i] = new Object[]{
                data.get(i).getVehicleID()
            };
        }
        return tableData;
    }
}