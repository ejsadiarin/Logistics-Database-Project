package Services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Database.DatabaseConnection;
import Models.Driver;

public class DriverDAO {
    private Connection connection;

    public DriverDAO() {
    }

    private Connection getConnection() throws SQLException {
       if (this.connection == null || this.connection.isClosed()) {
            this.connection = DatabaseConnection.getConnection();
        }
        return this.connection;
    }

    public void addDriver(Driver driver) throws SQLException {
        String query = "INSERT INTO drivers (driver_id, full_name, rate, contact_number, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, driver.getDriverID());
            stmt.setString(2, driver.getFullName());
            stmt.setDouble(3, driver.getRate());
            stmt.setString(4, driver.getContactNumber());
            stmt.setString(5, driver.getStatus().name());
            stmt.executeUpdate();
        }
    }

    public Driver getDriver(int driverID) throws SQLException {
        String query = "SELECT * FROM drivers WHERE driver_id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, driverID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Driver(
                        rs.getInt("driver_id"),
                        rs.getString("full_name"),
                        rs.getDouble("rate"),
                        rs.getString("contact_number"),
                        Driver.Status.valueOf(rs.getString("status"))
                    );
                }
            }
        }
        return null;
    }

    public List<Driver> getAllDrivers() throws SQLException {
        List<Driver> drivers = new ArrayList<>();
        String query = "SELECT * FROM drivers";
        try (Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                drivers.add(new Driver(
                    rs.getInt("driver_id"),
                    rs.getString("full_name"),
                    rs.getDouble("rate"),
                    rs.getString("contact_number"),
                    Driver.Status.valueOf(rs.getString("status"))
                ));
            }
        }
        return drivers;
    }

    public List<Driver> getAllAvailableDrivers() throws SQLException {
        // NOTE: driver availability indicator: status is "AVAILABLE"
        List<Driver> drivers = new ArrayList<>();
        String query = "SELECT * FROM drivers WHERE status = 'AVAILABLE'";
        try (Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                drivers.add(new Driver(
                    rs.getInt("driver_id"),
                    rs.getString("full_name"),
                    rs.getDouble("rate"),
                    rs.getString("contact_number"),
                    Driver.Status.valueOf(rs.getString("status"))
                ));
            }
        }
        return drivers;
    }

    public void updateDriver(Driver driver) throws SQLException {
        String query = "UPDATE drivers SET full_name = ?, rate = ?, contact_number = ?, status = ? WHERE driver_id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setString(1, driver.getFullName());
            stmt.setDouble(2, driver.getRate());
            stmt.setString(3, driver.getContactNumber());
            stmt.setString(4, driver.getStatus().name());
            stmt.setInt(5, driver.getDriverID());
            stmt.executeUpdate();
        }
    }

    public void deleteDriver(int driverID) throws SQLException {
        String query = "DELETE FROM drivers WHERE driver_id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, driverID);
            stmt.executeUpdate();
        }
    }
}
