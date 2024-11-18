package Services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Database.DatabaseConnection;
import Models.Vehicle;

public class VehicleDAO {
    private Connection connection;

    public VehicleDAO() {
    }

    private Connection getConnection() throws SQLException {
        if (this.connection == null || this.connection.isClosed()) {
            this.connection = DatabaseConnection.getConnection();
        }
        return this.connection;
    }

    public void addVehicle(Vehicle vehicle) throws SQLException {
        String query = "INSERT INTO vehicles (vehicle_id, plate_number, fuel_economy, last_maintenance_date, max_load_weight, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, vehicle.getVehicleID());
            stmt.setString(2, vehicle.getPlateNumber());
            stmt.setDouble(3, vehicle.getFuelEconomy());
            stmt.setDate(4, vehicle.getLastMaintenanceDate());
            stmt.setDouble(5, vehicle.getMaxLoadWeight());
            stmt.setString(6, vehicle.getStatus().name());
            stmt.executeUpdate();
        }
    }

    public Vehicle getVehicle(int vehicleID) throws SQLException {
        String query = "SELECT * FROM vehicles WHERE vehicle_id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, vehicleID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Vehicle(
                        rs.getInt("vehicle_id"),
                        rs.getString("plate_number"),
                        rs.getDouble("fuel_economy"),
                        rs.getDate("last_maintenance_date"),
                        rs.getDouble("max_load_weight"),
                        Vehicle.Status.valueOf(rs.getString("status"))
                    );
                }
            }
        }
        return null;
    }

    public List<Vehicle> getAllVehicles() throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles";
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                vehicles.add(new Vehicle(
                    rs.getInt("vehicle_id"),
                    rs.getString("plate_number"),
                    rs.getDouble("fuel_economy"),
                    rs.getDate("last_maintenance_date"),
                    rs.getDouble("max_load_weight"),
                    Vehicle.Status.valueOf(rs.getString("status"))
                ));
            }
        }
        return vehicles;
    }

    public void updateVehicle(Vehicle vehicle) throws SQLException {
        String query = "UPDATE vehicles SET plate_number = ?, fuel_economy = ?, last_maintenance_date = ?, max_load_weight = ?, status = ? WHERE vehicle_id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setString(1, vehicle.getPlateNumber());
            stmt.setDouble(2, vehicle.getFuelEconomy());
            stmt.setDate(3, vehicle.getLastMaintenanceDate());
            stmt.setDouble(4, vehicle.getMaxLoadWeight());
            stmt.setString(5, vehicle.getStatus().name());
            stmt.setInt(6, vehicle.getVehicleID());
            stmt.executeUpdate();
        }
    }

    public void deleteVehicle(int vehicleID) throws SQLException {
        String query = "DELETE FROM vehicles WHERE vehicle_id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, vehicleID);
            stmt.executeUpdate();
        }
    }
}
