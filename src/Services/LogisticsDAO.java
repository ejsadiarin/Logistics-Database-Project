package Services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Database.DatabaseConnection;
import Models.Logistics;

public class LogisticsDAO {
    private Connection connection;

    public LogisticsDAO() {
    }

    private Connection getConnection() throws SQLException {
        if (this.connection == null || this.connection.isClosed()) {
            this.connection = DatabaseConnection.getConnection();
        }
        return this.connection;
    }

    public void addLogistics(Logistics logistics) throws SQLException {
        String query = "INSERT INTO logistics (logistics_id, distance, normal_cost, status, schedule_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, logistics.getLogisticsID());
            stmt.setDouble(2, logistics.getDistance());
            stmt.setDouble(3, logistics.getNormalCost());
            stmt.setString(4, logistics.getStatus().name());
            stmt.setInt(5, logistics.getScheduleID());
            stmt.executeUpdate();
        }
    }

    public Logistics getLogistics(int logisticsID) throws SQLException {
        String query = "SELECT * FROM logistics WHERE logistics_id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, logisticsID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Logistics(
                        rs.getInt("logistics_id"),
                        rs.getDouble("distance"),
                        rs.getDouble("normal_cost"),
                        Logistics.Status.valueOf(rs.getString("status")),
                        rs.getInt("schedule_id")
                    );
                }
            }
        }
        return null;
    }

    public List<Logistics> getAllLogistics() throws SQLException {
        List<Logistics> logisticsList = new ArrayList<>();
        String query = "SELECT * FROM logistics";
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                logisticsList.add(new Logistics(
                    rs.getInt("logistics_id"),
                    rs.getDouble("distance"),
                    rs.getDouble("normal_cost"),
                    Logistics.Status.valueOf(rs.getString("status")),
                    rs.getInt("schedule_id")
                ));
            }
        }
        return logisticsList;
    }

    public void updateLogistics(Logistics logistics) throws SQLException {
        String query = "UPDATE logistics SET distance = ?, normal_cost = ?, status = ?, schedule_id = ? WHERE logistics_id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setDouble(1, logistics.getDistance());
            stmt.setDouble(2, logistics.getNormalCost());
            stmt.setString(3, logistics.getStatus().name());
            stmt.setInt(4, logistics.getScheduleID());
            stmt.setInt(5, logistics.getLogisticsID());
            stmt.executeUpdate();
        }
    }

    public void deleteLogistics(int logisticsID) throws SQLException {
        String query = "DELETE FROM logistics WHERE logistics_id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, logisticsID);
            stmt.executeUpdate();
        }
    }
}

