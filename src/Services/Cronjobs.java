package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Database.DatabaseConnection;

public class Cronjobs {


    // TODO: autoInTransit()
    // Checking if a logistics order and its driver and vehicle is in transit will involve the following data, operations assigned to Adrian Bernandino
    // a. Check if the current time is past the scheduled time under the schedule record of the logistics order AND if the status of the logistics order is “pending”
    // b. Set status of logistics order and its driver, and vehicle status to in transit
    // - check datetime NOW() if it passed the scheduled date of logisitics record
    // - check first if vehicle, driver, is 'AVAILABLE' and logistics is 'PENDING'
    // - if all is yes/true, set vehicle,driver,logisitics status to 'IN_TRANSIT'
    //
    // manual-based in transit TRANSACTION:
    // - NOTE: only need to set logistics status to 'IN_TRANSIT' -> changeLogisticsToInTransit() -- TODO: update the LogisticsController and add changeLogisticsToInTransit() function in LogisticsDAO
    // steps:
    // 1. check first if prev logistic status is 'PENDING' (statusIndex is 3) and then newStatus (selected) is 'IN_TRANSIT' -> see LogisticsController on how to do this
    // 2. then if all is true above: use dao.changeLogisticsToInTransit()
    // 3. inside dao.changeLogisticsToInTransit():
    //      - check if driver and vehicle is 'AVAILABLE' first
    //      - if yes/true: then set driver and vehicle status to 'IN_TRANSIT'

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
