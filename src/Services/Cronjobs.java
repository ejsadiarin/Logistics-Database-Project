package Services;

import Database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}
