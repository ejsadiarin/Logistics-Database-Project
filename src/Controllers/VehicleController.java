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
    
    public boolean createRecord(String plateNumber, String fuelEconomy, String lastMaintenanceDateStr, String maxLoadWeight) {
        try {
            double dbFuelEco = Double.parseDouble(fuelEconomy);
            double dbMaxLoadWeight = Double.parseDouble(maxLoadWeight);
            Date lastMaintenanceDate = null;
            if (lastMaintenanceDateStr != null && !lastMaintenanceDateStr.trim().isEmpty()) {
                lastMaintenanceDate = Date.valueOf(lastMaintenanceDateStr);
            }
            Vehicle newRecord = new Vehicle(0, plateNumber, dbFuelEco, lastMaintenanceDate, dbMaxLoadWeight, Vehicle.Status.AVAILABLE);
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
    
    public boolean updateRecord(int vehicleID, String plateNumber, String fuelEconomy, String lastMaintenanceDateStr, String maxLoadWeight, int statusIndex) {
        try {
            double dbFuelEco = Double.parseDouble(fuelEconomy);
            double dbMaxLoadWeight = Double.parseDouble(maxLoadWeight);
            Date lastMaintenanceDate = null;
            if (lastMaintenanceDateStr != null && !lastMaintenanceDateStr.trim().isEmpty()) {
                lastMaintenanceDate = Date.valueOf(lastMaintenanceDateStr);
            }
            Vehicle updatedRecord = new Vehicle(vehicleID, plateNumber, dbFuelEco, lastMaintenanceDate, dbMaxLoadWeight, Vehicle.Status.values()[statusIndex]);
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
