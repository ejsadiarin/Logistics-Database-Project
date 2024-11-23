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
        String updateLogisticsQuery = "UPDATE logistics SET distance = ?, normal_cost = ?, status = ?, schedule_id = ? WHERE logistics_id = ?";

        try (PreparedStatement updateLogisticsStmt = getConnection().prepareStatement(updateLogisticsQuery); ) {
            updateLogisticsStmt.setDouble(1, logistics.getDistance());
            updateLogisticsStmt.setDouble(2, logistics.getNormalCost());
            updateLogisticsStmt.setString(3, logistics.getStatus().name());
            updateLogisticsStmt.setInt(4, logistics.getScheduleID());
            updateLogisticsStmt.setInt(5, logistics.getLogisticsID());
            updateLogisticsStmt.executeUpdate();
        }
    }


    public void cancelLogisticsTransaction(Logistics logistics) throws SQLException {
        String updateLogisticsScheduleQuery =
            "UPDATE logistics SET schedule_id = NULL WHERE logistics_id = ?";
        String deleteRequestQuery =
            "DELETE FROM requests WHERE request_id = (SELECT request_id FROM schedules WHERE schedule_id = ?)";
        String deleteScheduleQuery =
            "DELETE FROM schedules WHERE schedule_id = ?";
        String changeStatusToCancelled =
            "UPDATE logistics SET status = 'CANCELLED' WHERE logistics_id = ?";

        Connection connection = null;
        PreparedStatement updateLogisticsScheduleStmt = null;
        PreparedStatement deleteRequestStmt = null;
        PreparedStatement deleteScheduleStmt = null;
        PreparedStatement changeStatusToCancelledStmt = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            // set schedule_id in logistics to NULL
            updateLogisticsScheduleStmt = connection.prepareStatement(updateLogisticsScheduleQuery);
            updateLogisticsScheduleStmt.setInt(1, logistics.getLogisticsID());
            updateLogisticsScheduleStmt.executeUpdate();

            // delete the associated request
            deleteRequestStmt = connection.prepareStatement(deleteRequestQuery);
            deleteRequestStmt.setInt(1, logistics.getScheduleID());
            deleteRequestStmt.executeUpdate();

            // delete the associated schedule
            deleteScheduleStmt = connection.prepareStatement(deleteScheduleQuery);
            deleteScheduleStmt.setInt(1, logistics.getScheduleID());
            deleteScheduleStmt.executeUpdate();

            // change status to cancel
            changeStatusToCancelledStmt = connection.prepareStatement(changeStatusToCancelled);
            changeStatusToCancelledStmt.setInt(1, logistics.getLogisticsID());
            changeStatusToCancelledStmt.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (updateLogisticsScheduleStmt != null) updateLogisticsScheduleStmt.close();
            if (deleteRequestStmt != null) deleteRequestStmt.close();
            if (deleteScheduleStmt != null) deleteScheduleStmt.close();
            if (connection != null) connection.setAutoCommit(true);
        }
    }
}
