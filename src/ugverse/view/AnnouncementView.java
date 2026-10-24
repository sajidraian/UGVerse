
package ugverse.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import ugverse.controller.AnnouncementController;
import ugverse.model.AnnouncementModel;

public class AnnouncementView extends JFrame {

    private final int userId;
    private final String userName;
    private final String userRole;

    private final AnnouncementController controller;

    private JTextField titleField;
    private JTextArea contentArea;
    private JTextArea announcementsArea;
    private JButton publishButton;

    public AnnouncementView(int userId, String name, String role) {
        this.userId = userId;
        this.userName = name;
        this.userRole = role;
        this.controller = new AnnouncementController();

        setTitle("UGVerse - Academic Announcements");
        setSize(850, 650);
        setMinimumSize(new Dimension(700, 550));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeUI();
        loadAnnouncements();
        configureRoleAccess();
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel heading = new JLabel("Academic Announcements");
        heading.setFont(new Font("SansSerif", Font.BOLD, 24));

        JLabel subtitle = new JLabel(
                "Welcome, " + userName + " — stay updated with university news."
        );

        JPanel headerPanel = new JPanel(new BorderLayout(5, 5));
        headerPanel.add(heading, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.SOUTH);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Announcement publishing form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(
                "Publish a New Announcement"
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Announcement Title:"), gbc);

        titleField = new JTextField(30);
        gbc.gridy = 1;
        formPanel.add(titleField, gbc);

        gbc.gridy = 2;
        formPanel.add(new JLabel("Announcement Content:"), gbc);

        contentArea = new JTextArea(5, 30);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);

        JScrollPane contentScroll = new JScrollPane(contentArea);
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        formPanel.add(contentScroll, gbc);

        publishButton = new JButton("Publish Announcement");
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        formPanel.add(publishButton, gbc);

        // Announcement feed
        announcementsArea = new JTextArea();
        announcementsArea.setEditable(false);
        announcementsArea.setLineWrap(true);
        announcementsArea.setWrapStyleWord(true);
        announcementsArea.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JScrollPane feedScroll = new JScrollPane(announcementsArea);
        feedScroll.setBorder(BorderFactory.createTitledBorder(
                "Published Announcements"
        ));

        JPanel centerPanel = new JPanel(new BorderLayout(0, 15));
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(feedScroll, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadAnnouncements());

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(refreshButton);
        bottomPanel.add(closeButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        publishButton.addActionListener(e -> publishAnnouncement());

        setContentPane(mainPanel);
    }

    private void configureRoleAccess() {
        boolean isFaculty = userRole != null
                && userRole.equalsIgnoreCase("Faculty");

        publishButton.setEnabled(isFaculty);
        titleField.setEditable(isFaculty);
        contentArea.setEditable(isFaculty);

        if (!isFaculty) {
            titleField.setToolTipText(
                    "Only Faculty members can publish announcements."
            );
            contentArea.setToolTipText(
                    "Only Faculty members can publish announcements."
            );
        }
    }

    private void publishAnnouncement() {
        String title = titleField.getText().trim();
        String content = contentArea.getText().trim();

        if (title.isEmpty() || content.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter both the title and content.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (title.length() > 200) {
            JOptionPane.showMessageDialog(
                    this,
                    "Title cannot exceed 200 characters.",
                    "Invalid Title",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        boolean success = controller.createAnnouncement(
                userId,
                userRole,
                title,
                content
        );

        if (success) {
            JOptionPane.showMessageDialog(
                    this,
                    "Announcement published successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            titleField.setText("");
            contentArea.setText("");
            loadAnnouncements();

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not publish the announcement. Please check "
                    + "your database connection and Faculty account.",
                    "Publish Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void loadAnnouncements() {
        List<AnnouncementModel> announcements =
                controller.getAllAnnouncements();

        StringBuilder output = new StringBuilder();

        if (announcements.isEmpty()) {
            output.append("No announcements have been published yet.");
        } else {
            SimpleDateFormat dateFormat =
                    new SimpleDateFormat("dd MMM yyyy, hh:mm a");

            for (AnnouncementModel announcement : announcements) {
                output.append("TITLE: ")
                        .append(announcement.getTitle())
                        .append("\n");

                output.append("By: ")
                        .append(announcement.getFacultyName())
                        .append("\n");

                Timestamp createdAt = announcement.getCreatedAt();

                if (createdAt != null) {
                    output.append("Date: ")
                            .append(dateFormat.format(createdAt))
                            .append("\n");
                }

                output.append("\n")
                        .append(announcement.getContent())
                        .append("\n");

                output.append(
                        "\n--------------------------------------------------"
                ).append("\n\n");
            }
        }

        announcementsArea.setText(output.toString());
        announcementsArea.setCaretPosition(0);
    }
}
