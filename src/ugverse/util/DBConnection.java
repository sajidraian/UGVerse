
package ugverse.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/UGVerse_DB?useSSL=false&serverTimezone=UTC";

    private static final String USER = "root";

    private static final String PASSWORD =
            System.getenv("UGVERSE_DB_PASSWORD");

    public static Connection getConnection() throws SQLException {

        if (PASSWORD == null || PASSWORD.isBlank()) {
            throw new SQLException(
                    "UGVERSE_DB_PASSWORD environment variable is not set!"
            );
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                    "MySQL JDBC Driver not found!", e
            );
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {

        try (Connection connection = getConnection()) {
            System.out.println(
                    "UGVerse Database Connected Successfully!"
            );

        } catch (SQLException e) {
            System.out.println(
                    "Database Connection Failed: " + e.getMessage()
            );
        }
    }
}
