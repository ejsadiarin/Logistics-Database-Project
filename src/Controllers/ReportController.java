package Controllers;

import Database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportController {

    public List<Object[]> getSalesReportByYear(String year) {
        String query = "SELECT DATE(date_paid) AS SalesDate, SUM(amount_paid) AS TotalSales, COUNT(amount_paid) AS SalesCount FROM customers WHERE YEAR(date_paid) = ? GROUP BY DATE(date_paid) ORDER BY SalesDate";
        return executeQuery(query, year);
    }

    public List<Object[]> getSalesReportByMonth(String year, String month) {
        String query = "SELECT DATE(date_paid) AS SalesDate, SUM(amount_paid) AS TotalSales, COUNT(amount_paid) AS SalesCount FROM customers WHERE YEAR(date_paid) = ? AND MONTH(date_paid) = ? GROUP BY DATE(date_paid) ORDER BY SalesDate";
        return executeQuery(query, year, month);
    }

    public List<Object[]> getDriverCompletedTripsReport(String year, String month) {
        String query = "SELECT d.driver_id, CONCAT(d.firstname, d.lastname) AS DriverName, MONTH(s.date) AS Month, COUNT(l.logistics_id) AS TotalTrips, SUM(l.distance) AS TotalKilometers FROM logistics l JOIN schedules s ON l.schedule_id = s.schedule_id JOIN drivers d ON s.driver_id = d.driver_id WHERE YEAR(s.date) = ? AND MONTH(s.date) = ? GROUP BY d.driver_id, MONTH(s.date) ORDER BY d.driver_id, Month";
        return executeQuery(query, year, month);
    }

    public List<Object[]> getVehicleCompletedTripsReport(String year, String month) {
        String query = "SELECT v.vehicle_id, CONCAT(v.plate_number, ' (', v.vehicle_id, ')') AS VehicleName, MONTH(s.date) AS Month, COUNT(l.logistics_id) AS TotalTrips, SUM(l.distance) AS TotalKilometers FROM logistics l JOIN schedules s ON l.schedule_id = s.schedule_id JOIN vehicles v ON s.vehicle_id = v.vehicle_id WHERE YEAR(s.date) = ? AND MONTH(s.date) = ? GROUP BY v.vehicle_id, MONTH(s.date) ORDER BY v.vehicle_id, Month";
        return executeQuery(query, year, month);
    }

    private List<Object[]> executeQuery(String query, String... params) {
        List<Object[]> data = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setString(i + 1, params[i]);
            }
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
}
