package Services;

import Database.DatabaseConnection;
import Models.Vehicle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<Vehicle> getAllAvailableVehicles() throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles WHERE status = 'AVAILABLE'";
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
        String checkQuery = "SELECT * FROM schedules WHERE vehicle_id = ?";
        String deleteQuery = "DELETE FROM vehicles WHERE vehicle_id = ?";
        try (
            PreparedStatement checkStmt = getConnection().prepareStatement(checkQuery);
            PreparedStatement deleteStmt = getConnection().prepareStatement(deleteQuery)
        ) {
            // BEGIN TRANSACTION
            connection.setAutoCommit(false);
            checkStmt.setInt(1, vehicleID);

            ResultSet resultSet = checkStmt.executeQuery();
            if(!resultSet.next()) {
                deleteStmt.setInt(1, vehicleID);
                deleteStmt.executeUpdate();
                connection.commit();  // Commit the transaction
            }
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }


    public void checkAndUpdateVehicleMaintenance(int scheduleID) throws SQLException {
        String updateVehicleStatusQuery =
                "UPDATE vehicles v " +
                "JOIN (" +
                "    SELECT s.vehicle_id " +
                "    FROM schedules s " +
                "    JOIN vehicles v2 ON s.vehicle_id = v2.vehicle_id " +
                "    WHERE s.date >= DATE_ADD(v2.last_maintenance_date, INTERVAL 6 MONTH) " +
                "    AND s.date = (SELECT MAX(s2.date) FROM schedules s2 WHERE s2.vehicle_id = s.vehicle_id) " +
                "    AND s.schedule_id = ? " + // This line ensures we only update the selected vehicle
                ") AS subquery ON v.vehicle_id = subquery.vehicle_id " +
                "SET v.status = 'NEEDS_MAINTENANCE' " +
                "WHERE v.status = 'AVAILABLE'";
    
        Connection connection = null;
        PreparedStatement updateStmt = null;
    
        try {
            connection = DatabaseConnection.getConnection();
            updateStmt = connection.prepareStatement(updateVehicleStatusQuery);
            updateStmt.setInt(1, scheduleID); // Set the scheduleID to filter the vehicle
            int rowsUpdated = updateStmt.executeUpdate();
            System.out.println(rowsUpdated + " vehicles updated to 'NEEDS_MAINTENANCE'");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (updateStmt != null) updateStmt.close();
            if (connection != null) connection.close();
        }
    }
}
