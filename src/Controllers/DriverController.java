package Controllers;

import java.sql.SQLException;
import java.util.List;

import Models.Driver;
import Services.DriverDAO;

public class DriverController {
    private final DriverDAO dao;
    
    public DriverController() {
        dao = new DriverDAO();
    }

    public Object[][] getDriverTableData() {
        List<Driver> data;
        int dataRows;
        try {
            data = dao.getAllDrivers();
            dataRows = data.size();
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        Object[][] tableData = new Object[dataRows][5];
        
        for(int i = 0; i < data.size(); i++) {
            tableData[i] = new Object[] {
                data.get(i).getDriverID(),
                data.get(i).getLastname(),
                data.get(i).getFirstname(),
                data.get(i).getRate(),
                data.get(i).getContactNumber(),
                data.get(i).getStatus()
            };
        }

        return tableData;
    }

    public boolean createRecord(String lastname, String firstname, String rate, String contactNumber) {
        try {
            double dbRate = Double.parseDouble(rate);
            Driver newRecord = new Driver(0, lastname, firstname, dbRate, contactNumber, Driver.Status.AVAILABLE);
            dao.addDriver(newRecord);
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    public boolean updateRecord(int driverID, String lastname, String firstname, String rate, String contactNumber, int statusIndex) {
        try {
            double dbRate = Double.parseDouble(rate);
            System.out.println(Driver.Status.values()[statusIndex]);
            Driver newRecord = new Driver(driverID, lastname, firstname, dbRate, contactNumber, Driver.Status.values()[statusIndex]);
            dao.updateDriver(newRecord);
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    public boolean deleteRecord(int driverID) {
        try {
            dao.deleteDriver(driverID);
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
}
