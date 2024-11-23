package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import Database.DatabaseConnection;

public class Cronjobs {

    public static void autoInTransit() throws SQLException {
        String findPendingLogisticsQuery = "SELECT l.logistics_id, s.driver_id, s.vehicle_id FROM logistics l JOIN schedules s ON l.schedule_id = s.schedule_id JOIN drivers d ON s.driver_id = d.driver_id JOIN vehicles v ON s.vehicle_id = v.vehicle_id WHERE l.status = 'PENDING' AND d.status = 'AVAILABLE' AND v.status = 'AVAILABLE' AND s.date <= NOW()";

        String updateLogisticsQuery = "UPDATE logistics SET status = 'IN_TRANSIT' WHERE logistics_id = ?";
        String updateDriverQuery = "UPDATE drivers SET status = 'IN_TRANSIT' WHERE driver_id = ?";
        String updateVehicleQuery = "UPDATE vehicles SET status = 'IN_TRANSIT' WHERE vehicle_id = ?";

        Connection connection = null;
        PreparedStatement findPendingLogisticsStmt = null;
        PreparedStatement updateLogisticsStmt = null;
        PreparedStatement updateDriverStmt = null;
        PreparedStatement updateVehicleStmt = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            // find all pending logistics record to be updated
            findPendingLogisticsStmt = connection.prepareStatement(findPendingLogisticsQuery);
            ResultSet rs = findPendingLogisticsStmt.executeQuery();

            while (rs.next()) {
                int logisticsId = rs.getInt("logistics_id");
                int driverId = rs.getInt("driver_id");
                int vehicleId = rs.getInt("vehicle_id");

                // update logistics status
                updateLogisticsStmt = connection.prepareStatement(updateLogisticsQuery);
                updateLogisticsStmt.setInt(1, logisticsId);
                updateLogisticsStmt.executeUpdate();

                // update driver status
                updateDriverStmt = connection.prepareStatement(updateDriverQuery);
                updateDriverStmt.setInt(1, driverId);
                updateDriverStmt.executeUpdate();

                // update vehicle status
                updateVehicleStmt = connection.prepareStatement(updateVehicleQuery);
                updateVehicleStmt.setInt(1, vehicleId);
                updateVehicleStmt.executeUpdate();
            }

            connection.commit();
        } catch (SQLException err) {
            if (connection != null) {
                connection.rollback();
            }
            err.printStackTrace();
            throw err;
        } finally {
            if (findPendingLogisticsStmt != null) findPendingLogisticsStmt.close();
            if (updateLogisticsStmt != null) updateLogisticsStmt.close();
            if (updateDriverStmt != null) updateDriverStmt.close();
            if (updateVehicleStmt != null) updateVehicleStmt.close();
            if (connection != null) connection.setAutoCommit(true);
        }
    }

    // TODO: need queries:
    // date-based:
    // - check if vehicle's assigned schedule date - vehicle's last_maintenance_date is >= 6 months, if true then vehicle's status will be 'NEEDS_MAINTENANCE', otherwise just leave it (prerequisite: status should be 'AVAILABLE' first before this check)
    // - if above is true: 
    //      - update vehicle status to 'NEEDS_MAINTENANCE'
    // - to update status back to 'AVAILABLE', needs manual intervention
    //
    // manual-based set status to 'NEEDS_MAINTENANCE':
    // - check if vehicle status is 'AVAILABLE', if true/yes then can set status to 'NEEDS_MAINTENANCE'
    // - to update status back to 'AVAILABLE', needs manual intervention
    //
    public static void checkAndUpdateVehicleMaintenance() throws SQLException {
        String updateVehicleStatusQuery =
            "UPDATE vehicles " +
            "SET status = 'NEEDS_MAINTENANCE' " +
            "WHERE status = 'AVAILABLE' " +
            "  AND vehicle_id IN (" +
            "    SELECT s.vehicle_id " +
            "    FROM schedules s " +
            "    JOIN vehicles v ON s.vehicle_id = v.vehicle_id " +
            "    WHERE s.date >= DATE_ADD(v.last_maintenance_date, INTERVAL 6 MONTH) " +
            "      AND s.date = (SELECT MAX(s2.date) FROM schedules s2 WHERE s2.vehicle_id = s.vehicle_id)" +
            "  )";

        Connection connection = null;
        PreparedStatement updateStmt = null;

        try {
            connection = DatabaseConnection.getConnection();
            updateStmt = connection.prepareStatement(updateVehicleStatusQuery);
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

    // TODO: needs Arrived cronjob
    // - update Logistics status to 'ARRIVED'
    // - update Driver status to 'UNAVAILABLE' --> then to be checked if fired or not???
    // - update Vehicle status to 'UNAVAILABLE' --> then to be checked if it needs maintenance
}
