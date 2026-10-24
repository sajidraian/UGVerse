
package ugverse.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import ugverse.controller.PostController;
import ugverse.model.PostModel;
import ugverse.util.UITheme;

public class PostView extends JFrame {

    private final int userId;
    private final String userName;
    private final String userRole;

    private final PostController controller = new PostController();
    private final JTextArea postInput = new JTextArea(4, 30);
    private final JPanel feedPanel = new JPanel();

    public PostView(int userId, String userName, String userRole) {
        this.userId = userId;
        this.userName = userName;
        this.userRole = userRole;

        setTitle("UGVerse - Community Posts");
        setSize(850, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(UITheme.BACKGROUND);

        add(createHeader(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);

        loadPosts();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UITheme.PRIMARY);
        header.setBorder(BorderFactory.createEmptyBorder(18, 22, 18, 22));

        JLabel title = new JLabel("Community Posts");
        title.setForeground(UITheme.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 23));

        JLabel user = new JLabel(userName + "  |  " + userRole);
        user.setForeground(UITheme.WHITE);
        user.setFont(UITheme.BODY_FONT);

        header.add(title, BorderLayout.WEST);
        header.add(user, BorderLayout.EAST);
        return header;
    }

    private JPanel createMainContent() {
        JPanel main = new JPanel(new BorderLayout(10, 15));
        main.setOpaque(false);
        main.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JPanel composer = new JPanel(new BorderLayout(8, 8));
        composer.setBackground(UITheme.WHITE);
        composer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER),
                BorderFactory.createEmptyBorder(14, 14, 14, 14)));

        JLabel heading = new JLabel("Share something with your university community");
        heading.setFont(UITheme.HEADING_FONT);
        heading.setForeground(UITheme.TEXT);

        postInput.setFont(UITheme.BODY_FONT);
        postInput.setLineWrap(true);
        postInput.setWrapStyleWord(true);
        postInput.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JButton publishButton = new JButton("Publish Post");
        UITheme.stylePrimaryButton(publishButton);
        publishButton.addActionListener(e -> publishPost());

        JButton refreshButton = new JButton("Refresh Feed");
        UITheme.styleSecondaryButton(refreshButton);
        refreshButton.addActionListener(e -> loadPosts());

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.setOpaque(false);
        actions.add(refreshButton);
        actions.add(publishButton);

        composer.add(heading, BorderLayout.NORTH);
        composer.add(new JScrollPane(postInput), BorderLayout.CENTER);
        composer.add(actions, BorderLayout.SOUTH);

        feedPanel.setLayout(new GridLayout(0, 1, 0, 12));
        feedPanel.setBackground(UITheme.BACKGROUND);

        JScrollPane feedScroll = new JScrollPane(feedPanel);
        feedScroll.setBorder(BorderFactory.createEmptyBorder());
        feedScroll.getViewport().setBackground(UITheme.BACKGROUND);

        main.add(composer, BorderLayout.NORTH);
        main.add(feedScroll, BorderLayout.CENTER);

        return main;
    }

    private void publishPost() {
        String content = postInput.getText().trim();

        try {
            controller.createPost(userId, content);
            postInput.setText("");
            JOptionPane.showMessageDialog(this, "Post published successfully!");
            loadPosts();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            showDatabaseError(ex);
        }
    }

    private void loadPosts() {
        feedPanel.removeAll();

        try {
            List<PostModel> posts = controller.getAllPosts();

            if (posts.isEmpty()) {
                JLabel empty = new JLabel("No posts yet. Be the first to share!");
                empty.setFont(UITheme.BODY_FONT);
                empty.setForeground(UITheme.MUTED);
                feedPanel.add(empty);
            } else {
                for (PostModel post : posts) {
                    feedPanel.add(createPostCard(post));
                }
            }
        } catch (SQLException ex) {
            showDatabaseError(ex);
        }

        feedPanel.revalidate();
        feedPanel.repaint();
    }

    private JPanel createPostCard(PostModel post) {
        JPanel card = new JPanel(new BorderLayout(8, 10));
        card.setBackground(UITheme.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER),
                BorderFactory.createEmptyBorder(14, 14, 14, 14)));

        JPanel authorPanel = new JPanel(new BorderLayout());
        authorPanel.setOpaque(false);

        JLabel author = new JLabel(
                post.getAuthorName() + "  •  " + post.getAuthorRole());
        author.setFont(new Font("SansSerif", Font.BOLD, 14));
        author.setForeground(UITheme.PRIMARY);

        JLabel date = new JLabel(
                post.getCreatedAt() == null ? "" : post.getCreatedAt());
        date.setFont(new Font("SansSerif", Font.PLAIN, 11));
        date.setForeground(UITheme.MUTED);

        authorPanel.add(author, BorderLayout.NORTH);
        authorPanel.add(date, BorderLayout.SOUTH);

        JTextArea content = new JTextArea(post.getContent());
        content.setFont(UITheme.BODY_FONT);
        content.setForeground(UITheme.TEXT);
        content.setLineWrap(true);
        content.setWrapStyleWord(true);
        content.setEditable(false);
        content.setFocusable(false);
        content.setOpaque(false);

        card.add(authorPanel, BorderLayout.NORTH);
        card.add(content, BorderLayout.CENTER);

        if (post.getUserId() == userId) {
            JButton editButton = new JButton("Edit");
            UITheme.styleSecondaryButton(editButton);
            editButton.addActionListener(e -> editPost(post));

            JButton deleteButton = new JButton("Delete");
            deleteButton.setFont(UITheme.BUTTON_FONT);
            deleteButton.setForeground(UITheme.DANGER);
            deleteButton.addActionListener(e -> deletePost(post));

            JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
            buttons.setOpaque(false);
            buttons.add(editButton);
            buttons.add(deleteButton);

            card.add(buttons, BorderLayout.SOUTH);
        }

        return card;
    }

    private void editPost(PostModel post) {
        JTextArea editor = new JTextArea(post.getContent(), 6, 30);
        editor.setLineWrap(true);
        editor.setWrapStyleWord(true);

        int result = JOptionPane.showConfirmDialog(
                this,
                new JScrollPane(editor),
                "Edit Your Post",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                controller.updatePost(post.getId(), userId, editor.getText());
                loadPosts();
                JOptionPane.showMessageDialog(this, "Post updated successfully!");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Validation Error", JOptionPane.WARNING_MESSAGE);
            } catch (SQLException ex) {
                showDatabaseError(ex);
            }
        }
    }

    private void deletePost(PostModel post) {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this post?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            try {
                controller.deletePost(post.getId(), userId);
                loadPosts();
                JOptionPane.showMessageDialog(this, "Post deleted successfully!");
            } catch (SQLException ex) {
                showDatabaseError(ex);
            }
        }
    }

    private void showDatabaseError(SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(
                this,
                "Could not complete the database operation.\n"
                + "Please check the database connection and try again.",
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new PostView(1, "Test Student", "Student").setVisible(true));
    }
}
