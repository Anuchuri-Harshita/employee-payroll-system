import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Handles creating and reusing a single JDBC connection to MySQL.
 * Update URL, USER, and PASSWORD to match your local MySQL setup.
 */
public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/payroll_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Satwika@7"; 

    private static Connection connection = null;

    private DBConnection() {
        // prevent instantiation
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL JDBC Driver not found on classpath.", e);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}