
package ugverse.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ugverse.util.DBConnection;

public class ProfileModel {

    private int userId;
    private String department;
    private String batch;
    private String bio;
    private String phone;

    public boolean loadProfile(int userId) throws SQLException {

        String sql = "SELECT department, batch, bio, phone "
                   + "FROM profiles WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    this.userId = userId;
                    this.department = rs.getString("department");
                    this.batch = rs.getString("batch");
                    this.bio = rs.getString("bio");
                    this.phone = rs.getString("phone");
                    return true;
                }
            }
        }

        return false;
    }

    public void saveProfile(int userId) throws SQLException {

        String sql = "INSERT INTO profiles "
                   + "(user_id, department, batch, bio, phone) "
                   + "VALUES (?, ?, ?, ?, ?) "
                   + "ON DUPLICATE KEY UPDATE "
                   + "department = VALUES(department), "
                   + "batch = VALUES(batch), "
                   + "bio = VALUES(bio), "
                   + "phone = VALUES(phone)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, department);
            ps.setString(3, batch);
            ps.setString(4, bio);
            ps.setString(5, phone);

            ps.executeUpdate();
        }
    }

    public int getUserId() {
        return userId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
