
package ugverse.controller;

import java.sql.SQLException;
import java.util.List;
import ugverse.model.PostModel;

public class PostController {

    // Read: Load all community posts
    public List<PostModel> getAllPosts() throws SQLException {
        return PostModel.getAllPosts();
    }

    // Create: Publish a new post
    public void createPost(int userId, String content)
            throws SQLException {

        validateUserId(userId);
        String cleanContent = validateContent(content);

        PostModel.createPost(userId, cleanContent);
    }

    // Update: Edit only the current user's post
    public void updatePost(int postId, int userId, String content)
            throws SQLException {

        validatePostId(postId);
        validateUserId(userId);
        String cleanContent = validateContent(content);

        PostModel.updatePost(postId, userId, cleanContent);
    }

    // Delete: Delete only the current user's post
    public void deletePost(int postId, int userId)
            throws SQLException {

        validatePostId(postId);
        validateUserId(userId);

        PostModel.deletePost(postId, userId);
    }

    // Validate user ID
    private void validateUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID.");
        }
    }

    // Validate post ID
    private void validatePostId(int postId) {
        if (postId <= 0) {
            throw new IllegalArgumentException("Invalid post ID.");
        }
    }

    // Validate post content
    private String validateContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Post content cannot be empty.");
        }

        String cleanContent = content.trim();

        if (cleanContent.length() > 5000) {
            throw new IllegalArgumentException(
                    "Post cannot exceed 5000 characters.");
        }

        return cleanContent;
    }
}
