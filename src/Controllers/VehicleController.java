package Controllers;

import Models.Vehicle;
import Services.VehicleDAO;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class VehicleController {
    private final VehicleDAO dao;
    
    public VehicleController() {
        dao = new VehicleDAO();
    }

    public Object[][] getVehicleTableData() {
        List<Vehicle> data;
        int dataRows;
        try {
            data = dao.getAllVehicles();
            dataRows = data.size();
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        Object[][] tableData = new Object[dataRows][5];
        
        for(int i = 0; i < data.size(); i++) {
            tableData[i] = new Object[] {
                data.get(i).getVehicleID(),
                data.get(i).getPlateNumber(),
                data.get(i).getFuelEconomy(),
                data.get(i).getLastMaintenanceDate(),
                data.get(i).getMaxLoadWeight(),
                data.get(i).getStatus()
            };
        }

        return tableData;
    }
    
    public boolean createRecord(String plateNumber, double fuelEconomy, String lastMaintenanceDateStr, double maxLoadWeight) {
        try {
            Date lastMaintenanceDate = null;
            if (lastMaintenanceDateStr != null && !lastMaintenanceDateStr.trim().isEmpty()) {
                lastMaintenanceDate = Date.valueOf(lastMaintenanceDateStr);
            }
            Vehicle newRecord = new Vehicle(0, plateNumber, fuelEconomy, lastMaintenanceDate, maxLoadWeight, Vehicle.Status.AVAILABLE);
            dao.addVehicle(newRecord);
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }
    
    public boolean updateRecord(int vehicleID, String plateNumber, double fuelEconomy, String lastMaintenanceDateStr, double maxLoadWeight, int statusIndex) {
        try {
            Date lastMaintenanceDate = null;
            if (lastMaintenanceDateStr != null && !lastMaintenanceDateStr.trim().isEmpty()) {
                lastMaintenanceDate = Date.valueOf(lastMaintenanceDateStr);
            }
            Vehicle updatedRecord = new Vehicle(vehicleID, plateNumber, fuelEconomy, lastMaintenanceDate, maxLoadWeight, Vehicle.Status.values()[statusIndex]);
            // TODO: if previous status is set to 'AVAILABLE' then can use updateMaintenanceRecord() only if the selected status is 'NEEDS_MAINTENANCE'
            dao.updateVehicle(updatedRecord);
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    public boolean deleteRecord(int driverID) {
        try {
            dao.deleteVehicle(driverID);
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
}
