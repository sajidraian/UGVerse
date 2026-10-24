
package ugverse.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import ugverse.util.DBConnection;

public class AnnouncementModel {

    private final int id;
    private final int facultyId;
    private final String facultyName;
    private final String title;
    private final String content;
    private final Timestamp createdAt;

    public AnnouncementModel(
            int id,
            int facultyId,
            String facultyName,
            String title,
            String content,
            Timestamp createdAt) {

        this.id = id;
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    // Publish a new announcement
    public static boolean createAnnouncement(
            int facultyId, String title, String content) {

        String sql = "INSERT INTO announcements "
                + "(faculty_id, title, content) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, facultyId);
            ps.setString(2, title);
            ps.setString(3, content);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve all announcements
    public static List<AnnouncementModel> getAllAnnouncements() {

        List<AnnouncementModel> announcements = new ArrayList<>();

        String sql = "SELECT a.id, a.faculty_id, u.name AS faculty_name, "
                + "a.title, a.content, a.created_at "
                + "FROM announcements a "
                + "JOIN users u ON a.faculty_id = u.id "
                + "ORDER BY a.created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                announcements.add(new AnnouncementModel(
                        rs.getInt("id"),
                        rs.getInt("faculty_id"),
                        rs.getString("faculty_name"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return announcements;
    }
}
