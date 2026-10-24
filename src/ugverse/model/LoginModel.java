
package ugverse.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ugverse.util.DBConnection;

public class LoginModel {

    private int userId;
    private String name;
    private String email;
    private String role;

    public boolean login(String email, String password)
            throws SQLException {

        String sql = "SELECT id, name, email, role "
                   + "FROM users WHERE email = ? AND password = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    this.userId = result.getInt("id");
                    this.name = result.getString("name");
                    this.email = result.getString("email");
                    this.role = result.getString("role");

                    return true;
                }
            }
        }

        return false;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
