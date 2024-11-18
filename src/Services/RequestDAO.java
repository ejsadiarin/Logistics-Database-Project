package Services;

import Database.DatabaseConnection;
import Models.Request;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RequestDAO {
    private Connection connection;

    public RequestDAO() {
    }

    private Connection getConnection() throws SQLException {
        if (this.connection == null || this.connection.isClosed()) {
            this.connection = DatabaseConnection.getConnection();
        }
        return this.connection;
    }

    public void addRequest(Request request) throws SQLException {
        String query = "INSERT INTO requests (requested_date, product, origin, destination, load_weight, customer_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setDate(1, request.getRequestedDate()); // Use java.sql.Date
            stmt.setString(2, request.getProduct());
            stmt.setString(3, request.getOrigin());
            stmt.setString(4, request.getDestination());
            stmt.setDouble(5, request.getLoadWeight());
            stmt.setInt(6, request.getCustomerID());
            stmt.executeUpdate();
        }
    }

    public Request getRequest(int requestID) throws SQLException {
        String query = "SELECT * FROM requests WHERE request_id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, requestID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Request(
                        rs.getInt("request_id"),
                        rs.getDate("requested_date"), // Retrieve as java.sql.Date
                        rs.getString("product"),
                        rs.getString("origin"),
                        rs.getString("destination"),
                        rs.getDouble("load_weight"),
                        rs.getInt("customer_id")
                    );
                }
            }
        }
        return null;
    }

    public List<Request> getAllRequests() throws SQLException {
        List<Request> requests = new ArrayList<>();
        String query = "SELECT * FROM requests";
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                requests.add(new Request(
                    rs.getInt("request_id"),
                    rs.getDate("requested_date"), // Retrieve as java.sql.Date
                    rs.getString("product"),
                    rs.getString("origin"),
                    rs.getString("destination"),
                    rs.getDouble("load_weight"),
                    rs.getInt("customer_id")
                ));
                int i = 0;
                System.out.println(requests.get(i++));
            }
        }
        return requests;
    }

    public void updateRequest(Request request) throws SQLException {
        String query = "UPDATE requests SET requested_date = ?, product = ?, origin = ?, destination = ?, load_weight = ?, customer_id = ? WHERE request_id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setDate(1, request.getRequestedDate()); // Use java.sql.Date
            stmt.setString(2, request.getProduct());
            stmt.setString(3, request.getOrigin());
            stmt.setString(4, request.getDestination());
            stmt.setDouble(5, request.getLoadWeight());
            stmt.setInt(6, request.getCustomerID());
            stmt.setInt(7, request.getRequestID());
            stmt.executeUpdate();
        }
    }

    public void deleteRequest(int requestID) throws SQLException {
        String query = "DELETE FROM requests WHERE request_id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, requestID);
            stmt.executeUpdate();
        }
    }
}
