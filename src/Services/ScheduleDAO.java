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
