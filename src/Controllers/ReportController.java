package Controllers;

import Database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportController {

    public List<Object[]> getSalesReportByYear(String year) {
        String query = "SELECT DATE(c.date_paid) AS SalesDate, SUM(c.amount_paid) AS TotalSales, COUNT(c.amount_paid) AS SalesCount, SUM(c.amount_paid - l.normal_cost) AS TotalProfit FROM customers c JOIN requests r ON c.customer_id = r.customer_id JOIN schedules s ON r.request_id = s.request_id JOIN logistics l ON s.schedule_id = l.schedule_id WHERE YEAR(c.date_paid) = ? AND l.status = 'ARRIVED' GROUP BY DATE(c.date_paid) ORDER BY SalesDate";
        return executeQuery(query, year);
    }

    public List<Object[]> getSalesReportByMonth(String year, String month) {
        String query = "SELECT DATE(c.date_paid) AS SalesDate, SUM(c.amount_paid) AS TotalSales, COUNT(c.amount_paid) AS SalesCount, SUM(c.amount_paid - l.normal_cost) AS TotalProfit FROM customers c JOIN requests r ON c.customer_id = r.customer_id JOIN schedules s ON r.request_id = s.request_id JOIN logistics l ON s.schedule_id = l.schedule_id WHERE YEAR(c.date_paid) = ? AND MONTH(c.date_paid) = ? AND l.status = 'ARRIVED' GROUP BY DATE(c.date_paid) ORDER BY SalesDate";
        return executeQuery(query, year, month);
    }

    public Object[] getSalesSummaryByYear(String year) {
        String query = "SELECT SUM(c.amount_paid) AS TotalSales, COUNT(c.amount_paid) AS SalesCount, SUM(c.amount_paid - l.normal_cost) AS TotalProfit FROM customers c JOIN requests r ON c.customer_id = r.customer_id JOIN schedules s ON r.request_id = s.request_id JOIN logistics l ON s.schedule_id = l.schedule_id WHERE YEAR(c.date_paid) = ?";
        return executeSummarySalesQuery(query, year);
    }

    public Object[] getSalesSummaryByMonth(String year, String month) {
        String query = "SELECT SUM(c.amount_paid) AS TotalSales, COUNT(c.amount_paid) AS SalesCount, SUM(c.amount_paid - l.normal_cost) AS TotalProfit FROM customers c JOIN requests r ON c.customer_id = r.customer_id JOIN schedules s ON r.request_id = s.request_id JOIN logistics l ON s.schedule_id = l.schedule_id WHERE YEAR(c.date_paid) = ? AND MONTH(c.date_paid) = ?";
        return executeSummarySalesQuery(query, year, month);
    }

    public List<Object[]> getDriverCompletedTripsReportByYear(String year, String driverId) {
        String query = "SELECT d.driver_id, CONCAT(d.firstname, ' ', d.lastname) AS DriverName, YEAR(s.date) AS Year, COUNT(l.logistics_id) AS TotalTrips, SUM(l.distance) AS TotalKilometers FROM logistics l JOIN schedules s ON l.schedule_id = s.schedule_id JOIN drivers d ON s.driver_id = d.driver_id WHERE YEAR(s.date) = ? AND d.driver_id = ? AND l.status = 'ARRIVED' GROUP BY d.driver_id, YEAR(s.date) ORDER BY d.driver_id, Year";
        return executeQuery(query, year, driverId);
    }

    public List<Object[]> getDriverCompletedTripsReportByMonth(String year, String month, String driverId) {
        String query = "SELECT d.driver_id, CONCAT(d.firstname, ' ', d.lastname) AS DriverName, MONTH(s.date) AS Month, COUNT(l.logistics_id) AS TotalTrips, SUM(l.distance) AS TotalKilometers FROM logistics l JOIN schedules s ON l.schedule_id = s.schedule_id JOIN drivers d ON s.driver_id = d.driver_id WHERE YEAR(s.date) = ? AND MONTH(s.date) = ? AND d.driver_id = ? AND l.status = 'ARRIVED' GROUP BY d.driver_id, MONTH(s.date) ORDER BY d.driver_id, Month";
        return executeQuery(query, year, month, driverId);
    }

    public List<Object[]> getVehicleCompletedTripsReportByYear(String year, String vehicleId) {
        String query = "SELECT v.vehicle_id, CONCAT(v.plate_number, ' (', v.vehicle_id, ')') AS VehicleName, YEAR(s.date) AS Year, COUNT(l.logistics_id) AS TotalTrips, SUM(l.distance) AS TotalKilometers FROM logistics l JOIN schedules s ON l.schedule_id = s.schedule_id JOIN vehicles v ON s.vehicle_id = v.vehicle_id WHERE YEAR(s.date) = ? AND v.vehicle_id = ? AND l.status = 'ARRIVED' GROUP BY v.vehicle_id, YEAR(s.date) ORDER BY v.vehicle_id, Year";
        return executeQuery(query, year, vehicleId);
    }

    public List<Object[]> getVehicleCompletedTripsReportByMonth(String year, String month, String vehicleId) {
        String query = "SELECT v.vehicle_id, CONCAT(v.plate_number, ' (', v.vehicle_id, ')') AS VehicleName, MONTH(s.date) AS Month, COUNT(l.logistics_id) AS TotalTrips, SUM(l.distance) AS TotalKilometers FROM logistics l JOIN schedules s ON l.schedule_id = s.schedule_id JOIN vehicles v ON s.vehicle_id = v.vehicle_id WHERE YEAR(s.date) = ? AND MONTH(s.date) = ? AND v.vehicle_id = ? AND l.status = 'ARRIVED' GROUP BY v.vehicle_id, MONTH(s.date) ORDER BY v.vehicle_id, Month";
        return executeQuery(query, year, month, vehicleId);
    }

    public List<Object[]> getCompletedTripsByDriver(String driverId) {
        String query = "SELECT l.logistics_id, s.date, l.distance, l.status FROM logistics l JOIN schedules s ON l.schedule_id = s.schedule_id WHERE s.driver_id = ? AND l.status = 'ARRIVED' ORDER BY s.date";
        return executeQuery(query, driverId);
    }

    public List<Object[]> getCompletedTripsByVehicle(String vehicleId) {
        String query = "SELECT l.logistics_id, s.date, l.distance, l.status FROM logistics l JOIN schedules s ON l.schedule_id = s.schedule_id WHERE s.vehicle_id = ? AND l.status = 'ARRIVED' ORDER BY s.date";
        return executeQuery(query, vehicleId);
    }

    public Object[] getDriverSummaryYear(String driverId) {
        String query = "SELECT SUM(l.distance) AS TotalKilometers, COUNT(l.logistics_id) AS TotalTrips FROM logistics l JOIN schedules s ON l.schedule_id = s.schedule_id WHERE s.driver_id = ? AND YEAR(s.date) = ? AND l.status = 'ARRIVED'";
        return executeSummaryQuery(query, driverId);
    }

    public Object[] getDriverSummaryMonth(String driverId, String year, String month) {
        String query = "SELECT SUM(l.distance) AS TotalKilometers, COUNT(l.logistics_id) AS TotalTrips FROM logistics l JOIN schedules s ON l.schedule_id = s.schedule_id WHERE s.driver_id = ? AND YEAR(s.date) = ? AND MONTH(s.date) = ? AND l.status = 'ARRIVED'";
        return executeSummaryQuery(query, driverId, year, month);
    }

    public Object[] getVehicleSummaryYear(String vehicleId) {
        String query = "SELECT SUM(l.distance) AS TotalKilometers, COUNT(l.logistics_id) AS TotalTrips FROM logistics l JOIN schedules s ON l.schedule_id = s.schedule_id WHERE s.vehicle_id = ? AND YEAR(s.date) = ? AND l.status = 'ARRIVED'";
        return executeSummaryQuery(query, vehicleId);
    }

    public Object[] getVehicleSummaryMonth(String vehicleId, String year, String month) {
        String query = "SELECT SUM(l.distance) AS TotalKilometers, COUNT(l.logistics_id) AS TotalTrips FROM logistics l JOIN schedules s ON l.schedule_id = s.schedule_id WHERE s.vehicle_id = ? AND YEAR(s.date) = ? AND MONTH(s.date) = ? AND l.status = 'ARRIVED'";
        return executeSummaryQuery(query, vehicleId, year, month);
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

    private Object[] executeSummarySalesQuery(String query, String... params) {
        Object[] summary = new Object[3];
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setString(i + 1, params[i]);
            }
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                summary[0] = rs.getObject("TotalSales");
                summary[1] = rs.getObject("SalesCount");
                summary[2] = rs.getObject("TotalProfit");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return summary;
    }

    private Object[] executeSummaryQuery(String query, String... params) {
        Object[] summary = new Object[2];
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setString(i + 1, params[i]);
            }
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                summary[0] = rs.getObject("TotalKilometers");
                summary[1] = rs.getObject("TotalTrips");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return summary;
    }
}
