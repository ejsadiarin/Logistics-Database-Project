import Database.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String query = "SELECT * FROM drivers";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("Connected to the database.");
            System.out.println("Employee Table:");

            // process the result set
            while (resultSet.next()) {
                // retrieve each column value by column name
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String department = resultSet.getString("department");
                double salary = resultSet.getDouble("salary");

                System.out.printf("ID: %d, Name: %s, Department: %s, Salary: %.2f%n", id, name, department, salary);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
