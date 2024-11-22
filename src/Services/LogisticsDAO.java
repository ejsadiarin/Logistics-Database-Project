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
                            rs.getInt("schedule_id"));
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
                        rs.getInt("schedule_id")));
            }
        }
        return logisticsList;
    }

    public void updateLogistics(Logistics logistics) throws SQLException {
        String checkIfPendingLogisticsQuery = "SELECT logistics_id FROM logistics WHERE status = 'PENDING' AND logistics_id = ? LIMIT 1";
        PreparedStatement checkPendingLogisticsStmt = null;
        ResultSet pendingLogisticsResultSet = null;

        String updateDriverStatusQuery = "UPDATE drivers SET status = 'AVAILABLE' WHERE driver_id = (SELECT d.driver_id FROM logistics l JOIN schedules s ON l.schedule_id = s.schedule_id JOIN drivers d ON s.driver_id = d.driver_id WHERE l.schedule_id = ?)";
        PreparedStatement updateDriverStatusStmt = null;

        String updateVehicleStatusQuery = "UPDATE vehicles SET status = 'AVAILABLE' WHERE vehicle_id = (SELECT v.vehicle_id FROM logistics l JOIN schedules s ON l.schedule_id = s.schedule_id JOIN vehicles v ON s.vehicle_id = v.vehicle_id WHERE l.schedule_id = ?)";
        PreparedStatement updateVehicleStatusStmt = null;

        String updateStatusQuery = "UPDATE logistics SET status = ? WHERE logistics_id = ?";
        PreparedStatement updateStatusStmt = null;

        String updateLogisticScheduleQuery = "UPDATE logistics SET schedule_id = NULL WHERE logistics_id = ?";
        PreparedStatement updateLogisticsScheduleStmt = null;

        String updateLogisticsQuery = "UPDATE logistics SET distance = ?, normal_cost = ?, status = ?, schedule_id = ? WHERE logistics_id = ?";
        PreparedStatement updateLogisticsStmt = null;

        String deleteRequestQuery = "DELETE FROM requests WHERE request_id = (SELECT r.request_id FROM logistics l JOIN schedules s ON l.schedule_id = s.schedule_id JOIN requests r ON s.request_id = r.request_id WHERE l.schedule_id = ?)";
        PreparedStatement deleteRequestStmt = null;

        String deleteScheduleQuery = "DELETE FROM schedules WHERE schedule_id = (SELECT s.schedule_id FROM logistics l JOIN schedules s ON l.schedule_id = s.schedule_id)";
        PreparedStatement deleteScheduleStmt = null;

        Connection connection = null;

        try {
            connection = getConnection();
            // only activate cancelling logistics transaction if status is changed to "CANCELLED"
            if (logistics.getStatus().name() == "CANCELLED") {
                connection.setAutoCommit(false);

                // check specific logistics order (if exists) && if status is "PENDING"
                checkPendingLogisticsStmt = getConnection().prepareStatement(checkIfPendingLogisticsQuery);
                checkPendingLogisticsStmt.setInt(1, logistics.getLogisticsID());
                pendingLogisticsResultSet = checkPendingLogisticsStmt.executeQuery();
                if (!pendingLogisticsResultSet.next()) {
                    throw new SQLException("Logistics status is not PENDING.");
                }

                // update driver status to "AVAILABLE"
                updateDriverStatusStmt = getConnection().prepareStatement(updateDriverStatusQuery);
                updateDriverStatusStmt.setInt(1, logistics.getScheduleID());
                updateDriverStatusStmt.executeQuery();
                
                // update vehicle status to "AVAILABLE"
                updateVehicleStatusStmt = getConnection().prepareStatement(updateVehicleStatusQuery);
                updateVehicleStatusStmt.setInt(1, logistics.getScheduleID());
                updateVehicleStatusStmt.executeQuery();
                
                // update logistics status to "CANCELLED"
                updateStatusStmt = getConnection().prepareStatement(updateStatusQuery);
                updateStatusStmt.setString(1, logistics.getStatus().name());
                updateStatusStmt.setInt(2, logistics.getLogisticsID());
                updateStatusStmt.executeQuery();

                // delete request and schedule records attached
                deleteRequestStmt = getConnection().prepareStatement(deleteRequestQuery);
                deleteRequestStmt.setInt(1, logistics.getScheduleID());
                deleteRequestStmt.executeQuery();
                deleteScheduleStmt = getConnection().prepareStatement(deleteScheduleQuery);
                deleteScheduleStmt.setInt(1, logistics.getScheduleID());
                deleteScheduleStmt.executeQuery();
                
                // set schedule_id FK of this to NULL
                updateLogisticsScheduleStmt = getConnection().prepareStatement(updateLogisticScheduleQuery);
                updateLogisticsScheduleStmt.setInt(1, logistics.getLogisticsID());
                updateLogisticsScheduleStmt.executeQuery();

                connection.commit();
            }
            updateLogisticsStmt = getConnection().prepareStatement(updateLogisticsQuery);
            updateLogisticsStmt.setDouble(1, logistics.getDistance());
            updateLogisticsStmt.setDouble(2, logistics.getNormalCost());
            updateLogisticsStmt.setString(3, logistics.getStatus().name());
            updateLogisticsStmt.setInt(4, logistics.getScheduleID());
            updateLogisticsStmt.setInt(5, logistics.getLogisticsID());
            updateLogisticsStmt.executeUpdate();
        } catch (SQLException err) {
            if (connection != null && logistics.getStatus().name() == "CANCELLED") {
                connection.rollback();
            }
            throw err;
        } finally {
            if (checkPendingLogisticsStmt != null) checkPendingLogisticsStmt.close();
            if (updateStatusStmt != null) updateStatusStmt.close();
            if (updateLogisticsStmt != null) updateLogisticsStmt.close();
            if (connection != null) connection.setAutoCommit(true);
        }
    }
}
