package Services;

import Database.DatabaseConnection;
import Models.Logistics;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public void cascadeInTransit(Logistics logistics) throws SQLException {
        String checkDriverAvailableQuery =
            "SELECT d.driver_id " +
            "FROM drivers d " +
            "JOIN schedules s ON d.driver_id = s.driver_id " +
            "WHERE s.schedule_id = ? AND d.status = 'AVAILABLE'";

        String checkVehicleAvailableQuery =
            "SELECT v.vehicle_id " +
            "FROM vehicles v " +
            "JOIN schedules s ON v.vehicle_id = s.vehicle_id " +
            "WHERE s.schedule_id = ? AND v.status = 'AVAILABLE'";

        String updateDriverInTransitQuery =
            "UPDATE drivers " +
            "SET status = 'IN_TRANSIT' " +
            "WHERE driver_id = (SELECT driver_id FROM schedules WHERE schedule_id = ?)";

        String updateVehicleInTransitQuery =
            "UPDATE vehicles " +
            "SET status = 'IN_TRANSIT' " +
            "WHERE vehicle_id = (SELECT vehicle_id FROM schedules WHERE schedule_id = ?)";

        String updateLogisticsInTransitQuery =
            "UPDATE logistics " +
            "SET status = 'IN_TRANSIT' " +
            "WHERE logistics_id = ?";

        Connection connection = null;
        PreparedStatement checkDriverAvailableStmt = null;
        PreparedStatement checkVehicleAvailableStmt = null;
        PreparedStatement updateDriverStmt = null;
        PreparedStatement updateVehicleStmt = null;
        PreparedStatement updateLogisticsStmt = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            checkDriverAvailableStmt = connection.prepareStatement(checkDriverAvailableQuery);
            checkDriverAvailableStmt.setInt(1, logistics.getScheduleID());
            ResultSet driverResult = checkDriverAvailableStmt.executeQuery();
            if (!driverResult.next()) {
                throw new SQLException("Driver is not available.");
            }

            checkVehicleAvailableStmt = connection.prepareStatement(checkVehicleAvailableQuery);
            checkVehicleAvailableStmt.setInt(1, logistics.getScheduleID());
            ResultSet vehicleResult = checkVehicleAvailableStmt.executeQuery();
            if (!vehicleResult.next()) {
                throw new SQLException("Vehicle is not available.");
            }

            updateDriverStmt = connection.prepareStatement(updateDriverInTransitQuery);
            updateDriverStmt.setInt(1, logistics.getScheduleID());
            updateDriverStmt.executeUpdate();

            updateVehicleStmt = connection.prepareStatement(updateVehicleInTransitQuery);
            updateVehicleStmt.setInt(1, logistics.getScheduleID());
            updateVehicleStmt.executeUpdate();

            updateLogisticsStmt = connection.prepareStatement(updateLogisticsInTransitQuery);
            updateLogisticsStmt.setInt(1, logistics.getLogisticsID());
            updateLogisticsStmt.executeUpdate();

            connection.commit();
            System.out.println("Logistics, driver, and vehicle successfully updated to 'IN_TRANSIT'.");
        } catch (SQLException err) {
            if (connection != null) {
                connection.rollback();
            }
            err.printStackTrace();
            throw err;
        } finally {
            if (checkDriverAvailableStmt != null) checkDriverAvailableStmt.close();
            if (checkVehicleAvailableStmt != null) checkVehicleAvailableStmt.close();
            if (updateDriverStmt != null) updateDriverStmt.close();
            if (updateVehicleStmt != null) updateVehicleStmt.close();
            if (updateLogisticsStmt != null) updateLogisticsStmt.close();
            if (connection != null) connection.setAutoCommit(true);
        }
    }

    public void arrivedUpdate(int logisticsID) throws SQLException {
        String updateLogisticsStatusQuery = "UPDATE logistics SET status = 'ARRIVED' WHERE logistics_id = ?";
        String updateDriverStatusQuery = "UPDATE drivers SET status = 'AVAILABLE' WHERE driver_id = (SELECT driver_id FROM schedules WHERE schedule_id = (SELECT schedule_id FROM logistics WHERE logistics_id = ?))";
        String updateVehicleStatusQuery = "UPDATE vehicles SET status = 'AVAILABLE', last_maintenance_date = NOW() WHERE vehicle_id = (SELECT vehicle_id FROM schedules WHERE schedule_id = (SELECT schedule_id FROM logistics WHERE logistics_id = ?))";

        Connection connection = null;
        PreparedStatement updateLogisticsStatusStmt = null;
        PreparedStatement updateDriverStatusStmt = null;
        PreparedStatement updateVehicleStatusStmt = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            // Update logistics status to 'ARRIVED'
            updateLogisticsStatusStmt = connection.prepareStatement(updateLogisticsStatusQuery);
            updateLogisticsStatusStmt.setInt(1, logisticsID);
            updateLogisticsStatusStmt.executeUpdate();

            // Update driver status to 'AVAILABLE'
            updateDriverStatusStmt = connection.prepareStatement(updateDriverStatusQuery);
            updateDriverStatusStmt.setInt(1, logisticsID);
            updateDriverStatusStmt.executeUpdate();

            // Update vehicle status to 'AVAILABLE' and last maintenance date to NOW
            updateVehicleStatusStmt = connection.prepareStatement(updateVehicleStatusQuery);
            updateVehicleStatusStmt.setInt(1, logisticsID);
            updateVehicleStatusStmt.executeUpdate();

            connection.commit();
        } catch (SQLException err) {
            if (connection != null) {
                connection.rollback();
            }
            err.printStackTrace();
            throw err;
        } finally {
            if (updateLogisticsStatusStmt != null) updateLogisticsStatusStmt.close();
            if (updateDriverStatusStmt != null) updateDriverStatusStmt.close();
            if (updateVehicleStatusStmt != null) updateVehicleStatusStmt.close();
            if (connection != null) connection.setAutoCommit(true);
        }
    }
}
