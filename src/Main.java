import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/ccinfom"; // NOTE: create database called "ccinfom" in your mysql
        // configure mysql to have user "root" with password: "12345"
        String username = "root";
        String password = "12345";

        String query = "SELECT * FROM employee";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
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
