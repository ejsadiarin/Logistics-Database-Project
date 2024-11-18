package Controllers;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import Models.Vehicle;
import Services.VehicleDAO;

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

    public boolean createRecord(String plateNumber, String fuelEconomy, Date lastMaintenanceDate, String maxLoadWeight) {
        try {
            double dbFuelEco = Double.parseDouble(fuelEconomy);
            double dbMaxLoadWeight = Double.parseDouble(maxLoadWeight);
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

    public boolean updateRecord(int vehicleID, String plateNumber, String fuelEconomy, Date lastMaintenanceDate, String maxLoadWeight, int statusIndex) {
        try {
            double dbFuelEco = Double.parseDouble(fuelEconomy);
            double dbMaxLoadWeight = Double.parseDouble(maxLoadWeight);
            System.out.println(Vehicle.Status.values()[statusIndex]);
            Vehicle newRecord = new Vehicle(vehicleID, plateNumber, dbFuelEco, lastMaintenanceDate, dbMaxLoadWeight, Vehicle.Status.values()[statusIndex]);
            dao.updateVehicle(newRecord);
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
