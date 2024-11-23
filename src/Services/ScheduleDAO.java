package Services;

import Database.DatabaseConnection;
import Models.Schedule;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {
    private Connection connection;

    public ScheduleDAO() {
    }

    private Connection getConnection() throws SQLException {
        if (this.connection == null || this.connection.isClosed()) {
            this.connection = DatabaseConnection.getConnection();
        }
        return this.connection;
    }

    public double[] getRateAndEconomy(int scheduleID) throws SQLException {
        String query = "SELECT drivers.rate, vehicles.fuel_economy FROM schedules JOIN drivers ON schedules.driver_id = drivers.driver_id JOIN vehicles ON schedules.vehicle_id = vehicles.vehicle_id WHERE schedule_id = ?";
        double[] result = new double[2];
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, scheduleID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    result[0] = rs.getDouble("drivers.rate");
                    result[1] = rs.getDouble("vehicles.fuel_economy");
                }
            }
        }
        return result;
    }

    public List<Schedule> getAllSchedulesWithNoLogistics() throws SQLException {
        List<Schedule> schedules = new ArrayList<>();
        String query = "SELECT s.schedule_id, s.date, s.driver_id, s.vehicle_id, s.request_id " +
                        "FROM schedules s " +
                        "LEFT JOIN logistics l ON s.schedule_id = l.schedule_id " +
                        "WHERE l.schedule_id IS NULL;";
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                schedules.add(new Schedule(
                    rs.getInt("schedule_id"),
                    rs.getTimestamp("date"),
                    rs.getInt("driver_id"),
                    rs.getInt("vehicle_id"),
                    rs.getInt("request_id")
                ));
            }
        }
        return schedules;
    }


    public void addSchedule(Schedule schedule) throws SQLException {
        String query = "INSERT INTO schedules (schedule_id, date, driver_id, vehicle_id, request_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, schedule.getScheduleID());
            stmt.setTimestamp(2, schedule.getDate());
            stmt.setInt(3, schedule.getDriverID());
            stmt.setInt(4, schedule.getVehicleID());
            stmt.setInt(5, schedule.getRequestID());
            stmt.executeUpdate();
        }
    }

    public void addScheduleTransaction(Schedule schedule) throws SQLException {
        String checkVehicleQuery = "SELECT v.vehicle_id FROM vehicles v JOIN requests r ON v.max_load_weight >= r.load_weight WHERE r.request_id = ? AND v.status = 'AVAILABLE' LIMIT 1";
        String checkDriverQuery = "SELECT driver_id FROM drivers WHERE status = 'AVAILABLE' LIMIT 1";
        String newScheduleQuery = "INSERT INTO schedules (schedule_id, date, driver_id, vehicle_id, request_id) VALUES (?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement checkVehicleStmt = null;
        PreparedStatement checkDriverStmt = null;
        PreparedStatement newScheduleQueryStmt = null;
        ResultSet vehicleResultSet = null;
        ResultSet driverResultSet = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            // check vehicle
            checkVehicleStmt = connection.prepareStatement(checkVehicleQuery);
            checkVehicleStmt.setInt(1, schedule.getRequestID());
            vehicleResultSet = checkVehicleStmt.executeQuery();
            if (!vehicleResultSet.next()) {
                throw new SQLException("No available vehicle found for the given request.");
            }
            int availableVehicleID = vehicleResultSet.getInt("vehicle_id");

            // check driver
            checkDriverStmt = connection.prepareStatement(checkDriverQuery);
            driverResultSet = checkDriverStmt.executeQuery();
            if (!driverResultSet.next()) {
                throw new SQLException("No available driver found.");
            }
            int availableDriverID = driverResultSet.getInt("driver_id");

            // insert as new schedule record if "AVAILABLE" driver and vehicle
            newScheduleQueryStmt = connection.prepareStatement(newScheduleQuery);
            newScheduleQueryStmt.setInt(1, schedule.getScheduleID());
            newScheduleQueryStmt.setTimestamp(2, schedule.getDate());
            newScheduleQueryStmt.setInt(3, availableDriverID);
            newScheduleQueryStmt.setInt(4, availableVehicleID);
            newScheduleQueryStmt.setInt(5, schedule.getRequestID());
            newScheduleQueryStmt.executeUpdate();

            connection.commit(); 
        } catch (SQLException err) {
            if (connection != null) {
                connection.rollback();
            }
            throw err;
        } finally {
            if (vehicleResultSet != null) vehicleResultSet.close();
            if (driverResultSet != null) driverResultSet.close();
            if (checkVehicleStmt != null) checkVehicleStmt.close();
            if (checkDriverStmt != null) checkDriverStmt.close();
            if (newScheduleQueryStmt != null) newScheduleQueryStmt.close();
            if (connection != null) connection.setAutoCommit(true);
        }
    }


    public Schedule getSchedule(int scheduleID) throws SQLException {
        String query = "SELECT * FROM schedules WHERE schedule_id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, scheduleID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Schedule(
                        rs.getInt("schedule_id"),
                        rs.getTimestamp("date"),
                        rs.getInt("driver_id"),
                        rs.getInt("vehicle_id"),
                        rs.getInt("request_id")
                    );
                }
            }
        }
        return null;
    }

    public List<Schedule> getAllSchedules() throws SQLException {
        List<Schedule> schedules = new ArrayList<>();
        String query = "SELECT * FROM schedules";
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                schedules.add(new Schedule(
                    rs.getInt("schedule_id"),
                    rs.getTimestamp("date"),
                    rs.getInt("driver_id"),
                    rs.getInt("vehicle_id"),
                    rs.getInt("request_id")
                ));
            }
        }
        return schedules;
    }

    public void updateSchedule(Schedule schedule) throws SQLException {
        String query = "UPDATE schedules SET date = ?, driver_id = ?, vehicle_id = ?, request_id = ? WHERE schedule_id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setTimestamp(1, schedule.getDate());
            stmt.setInt(2, schedule.getDriverID());
            stmt.setInt(3, schedule.getVehicleID());
            stmt.setInt(4, schedule.getRequestID());
            stmt.setInt(5, schedule.getScheduleID());
            stmt.executeUpdate();
        }
    }

    public void deleteSchedule(int scheduleID) throws SQLException {
        String query = "DELETE FROM schedules WHERE schedule_id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, scheduleID);
            stmt.executeUpdate();
        }
    }
}
