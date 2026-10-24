
package ugverse.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ugverse.util.DBConnection;

public class PostModel {

    private int id;
    private int userId;
    private String authorName;
    private String authorRole;
    private String content;
    private String createdAt;

    public PostModel() {
    }

    public PostModel(int id, int userId, String authorName,
            String authorRole, String content, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.authorName = authorName;
        this.authorRole = authorRole;
        this.content = content;
        this.createdAt = createdAt;
    }

    // Get all posts
    public static List<PostModel> getAllPosts() throws SQLException {

        List<PostModel> posts = new ArrayList<>();

        String sql = "SELECT p.id, p.user_id, u.name, u.role, "
                + "p.content, p.created_at "
                + "FROM posts p "
                + "JOIN users u ON p.user_id = u.id "
                + "ORDER BY p.created_at DESC, p.id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PostModel post = new PostModel(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("role"),
                        rs.getString("content"),
                        rs.getString("created_at")
                );

                posts.add(post);
            }
        }

        return posts;
    }

    // Create a post
    public static void createPost(int userId, String content)
            throws SQLException {

        String sql = "INSERT INTO posts (user_id, content) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, content);
            ps.executeUpdate();
        }
    }

    // Update only the current user's post
    public static boolean updatePost(int postId, int userId, String content)
            throws SQLException {

        String sql = "UPDATE posts SET content = ? "
                + "WHERE id = ? AND user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, content);
            ps.setInt(2, postId);
            ps.setInt(3, userId);

            return ps.executeUpdate() > 0;
        }
    }

    // Delete only the current user's post
    public static boolean deletePost(int postId, int userId)
            throws SQLException {

        String sql = "DELETE FROM posts WHERE id = ? AND user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, postId);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;
        }
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorRole() {
        return authorRole;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
