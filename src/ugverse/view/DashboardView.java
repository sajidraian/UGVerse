
package ugverse.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ugverse.util.UITheme;

public class DashboardView extends JFrame {

    private final int userId;
    private final String name;
    private final String role;

    private JButton dashboardButton;
    private JButton profileButton;
    private JButton postsButton;

    public DashboardView(int userId, String name, String role) {

        this.userId = userId;
        this.name = name;
        this.role = role;

        setTitle("UGVerse | Dashboard");
        setSize(1050, 680);
        setMinimumSize(new Dimension(800, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BACKGROUND);

        // =========================
        // SIDEBAR
        // =========================
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(UITheme.DARK_BLUE);
        sidebar.setPreferredSize(new Dimension(230, 680));
        sidebar.setBorder(
                BorderFactory.createEmptyBorder(25, 15, 20, 15)
        );

        JLabel logo = new JLabel("UGVerse");
        logo.setFont(new Font("SansSerif", Font.BOLD, 27));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(LEFT_ALIGNMENT);

        JLabel logoSubtitle = new JLabel("UNIVERSITY COMMUNITY");
        logoSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 10));
        logoSubtitle.setForeground(new Color(190, 215, 250));
        logoSubtitle.setAlignmentX(LEFT_ALIGNMENT);

        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(5));
        sidebar.add(logoSubtitle);
        sidebar.add(Box.createVerticalStrut(40));

        JLabel menuLabel = new JLabel("MAIN MENU");
        menuLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        menuLabel.setForeground(new Color(180, 205, 240));
        menuLabel.setAlignmentX(LEFT_ALIGNMENT);

        sidebar.add(menuLabel);
        sidebar.add(Box.createVerticalStrut(12));

        dashboardButton = createNavButton("Dashboard");
        profileButton = createNavButton("My Profile");
        postsButton = createNavButton("Community Posts");

        sidebar.add(dashboardButton);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(profileButton);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(postsButton);

        sidebar.add(Box.createVerticalGlue());

        // User information
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        userPanel.setOpaque(false);
        userPanel.setAlignmentX(LEFT_ALIGNMENT);
        userPanel.setBorder(
                BorderFactory.createMatteBorder(
                        1, 0, 0, 0, new Color(65, 95, 140)
                )
        );

        JLabel userName = new JLabel(name);
        userName.setFont(new Font("SansSerif", Font.BOLD, 14));
        userName.setForeground(Color.WHITE);
        userName.setBorder(
                BorderFactory.createEmptyBorder(15, 0, 5, 0)
        );

        JLabel userRole = new JLabel(role);
        userRole.setFont(new Font("SansSerif", Font.PLAIN, 12));
        userRole.setForeground(new Color(190, 215, 250));

        JButton logoutButton = createNavButton("Logout");
        logoutButton.setForeground(new Color(255, 210, 210));

        userPanel.add(userName);
        userPanel.add(userRole);
        userPanel.add(Box.createVerticalStrut(15));
        userPanel.add(logoutButton);

        sidebar.add(userPanel);

        // =========================
        // MAIN CONTENT
        // =========================
        JPanel main = new JPanel(new BorderLayout(0, 20));
        main.setBackground(UITheme.BACKGROUND);
        main.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 30, 30)
        );

        JLabel pageTitle = new JLabel("Dashboard");
        pageTitle.setFont(UITheme.TITLE_FONT);
        pageTitle.setForeground(UITheme.DARK_BLUE);

        JLabel userInfo = new JLabel(
                "Signed in as " + role + "   |   ID: " + userId
        );
        userInfo.setFont(UITheme.BODY_FONT);
        userInfo.setForeground(UITheme.MUTED);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.add(pageTitle, BorderLayout.NORTH);
        topBar.add(userInfo, BorderLayout.SOUTH);

        JPanel dashboardContent = createDashboardContent();

        main.add(topBar, BorderLayout.NORTH);
        main.add(dashboardContent, BorderLayout.CENTER);

        root.add(sidebar, BorderLayout.WEST);
        root.add(main, BorderLayout.CENTER);

        setContentPane(root);

        // =========================
        // SIDEBAR NAVIGATION
        // =========================

        dashboardButton.addActionListener(e -> {
            setActiveButton(dashboardButton);
        });

        profileButton.addActionListener(e -> {
            setActiveButton(profileButton);
            new ProfileView(userId, name, role).setVisible(true);
        });

        postsButton.addActionListener(e -> {
            setActiveButton(postsButton);
            new PostView(userId, name, role).setVisible(true);
        });

        logoutButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to log out?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        setActiveButton(dashboardButton);
    }

    // =========================
    // DASHBOARD CONTENT
    // =========================
    private JPanel createDashboardContent() {

        JPanel dashboard = new JPanel(new BorderLayout(0, 20));
        dashboard.setOpaque(false);

        String welcomeMessage;
        String featureTitle;
        String featureDescription;

        // Role-based dashboard
        if (role != null && role.equalsIgnoreCase("Faculty")) {

            welcomeMessage =
                    "Share knowledge and guide your students.";

            featureTitle = "Academic Resources";

            featureDescription =
                    "Share study materials, academic announcements "
                    + "and research opportunities.";

        } else if (role != null && role.equalsIgnoreCase("Alumni")) {

            welcomeMessage =
                    "Inspire students with your experience.";

            featureTitle = "Career & Mentorship";

            featureDescription =
                    "Help students explore careers, discover "
                    + "opportunities and learn from your experience.";

        } else {

            welcomeMessage =
                    "Learn, connect and grow with your community.";

            featureTitle = "Study & Career Resources";

            featureDescription =
                    "Explore learning resources, internships "
                    + "and opportunities for your future.";
        }

        // Welcome banner
        JPanel banner = new JPanel(new BorderLayout());
        banner.setBackground(UITheme.PRIMARY);
        banner.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        );

        JLabel welcome = new JLabel(
                "<html><div style='color:white;'>"
                + "<span style='font-size:22pt;font-weight:bold;'>"
                + "Welcome back, " + escapeHtml(name) + "!"
                + "</span><br><br>"
                + "<span style='font-size:12pt;'>"
                + escapeHtml(welcomeMessage)
                + "</span></div></html>"
        );

        banner.add(welcome, BorderLayout.CENTER);

        // Feature cards
        JPanel cards = new JPanel(new GridLayout(1, 2, 18, 0));
        cards.setOpaque(false);

        cards.add(createFeatureCard(
                featureTitle,
                featureDescription,
                "Explore Resources"
        ));

        cards.add(createFeatureCard(
                "Community Posts",
                "Share updates, discuss ideas and connect "
                + "with your university community.",
                "Explore Community"
        ));

        // Workspace section
        JPanel lower = new JPanel(new BorderLayout());
        lower.setOpaque(false);

        JLabel sectionTitle = new JLabel("Your Workspace");
        sectionTitle.setFont(UITheme.HEADING_FONT);
        sectionTitle.setForeground(UITheme.DARK_BLUE);
        sectionTitle.setBorder(
                BorderFactory.createEmptyBorder(0, 0, 12, 0)
        );

        lower.add(sectionTitle, BorderLayout.NORTH);
        lower.add(cards, BorderLayout.CENTER);

        dashboard.add(banner, BorderLayout.NORTH);
        dashboard.add(lower, BorderLayout.CENTER);

        return dashboard;
    }

    // =========================
    // FEATURE CARDS
    // =========================
    private JPanel createFeatureCard(
            String title,
            String description,
            String buttonText) {

        JPanel card = new JPanel(new BorderLayout(0, 15));
        card.setBackground(Color.WHITE);
        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UITheme.BORDER),
                        BorderFactory.createEmptyBorder(
                                20, 18, 20, 18
                        )
                )
        );

        JLabel cardTitle = new JLabel(title);
        cardTitle.setFont(UITheme.HEADING_FONT);
        cardTitle.setForeground(UITheme.DARK_BLUE);

        JLabel cardDescription = new JLabel(
                "<html><div style='width:180px;'>"
                + escapeHtml(description)
                + "</div></html>"
        );
        cardDescription.setFont(UITheme.BODY_FONT);
        cardDescription.setForeground(UITheme.MUTED);

        JButton actionButton = new JButton(buttonText);
        UITheme.stylePrimaryButton(actionButton);

        // =========================
        // FEATURE CARD ACTIONS
        // =========================
        actionButton.addActionListener(e -> {

            if ("Explore Community".equals(buttonText)) {

                new PostView(
                        userId, name, role
                ).setVisible(true);

            } else if ("Explore Resources".equals(buttonText)
                    && role != null
                    && role.equalsIgnoreCase("Faculty")) {

                new AnnouncementView(
                        userId, name, role
                ).setVisible(true);

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "This feature will be implemented next.",
                        title,
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(
                new BoxLayout(textPanel, BoxLayout.Y_AXIS)
        );

        textPanel.add(cardTitle);
        textPanel.add(Box.createVerticalStrut(12));
        textPanel.add(cardDescription);

        card.add(textPanel, BorderLayout.CENTER);
        card.add(actionButton, BorderLayout.SOUTH);

        return card;
    }

    // =========================
    // SIDEBAR BUTTON
    // =========================
    private JButton createNavButton(String text) {

        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(UITheme.DARK_BLUE);
        button.setHorizontalAlignment(JButton.LEFT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        button.setPreferredSize(new Dimension(195, 44));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBorder(
                BorderFactory.createEmptyBorder(10, 12, 10, 8)
        );

        button.setCursor(
                new java.awt.Cursor(
                        java.awt.Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    // =========================
    // ACTIVE NAVIGATION BUTTON
    // =========================
    private void setActiveButton(JButton activeButton) {

        dashboardButton.setBackground(UITheme.DARK_BLUE);
        profileButton.setBackground(UITheme.DARK_BLUE);
        postsButton.setBackground(UITheme.DARK_BLUE);

        activeButton.setBackground(UITheme.PRIMARY);
    }

    // =========================
    // HTML ESCAPING
    // =========================
    private String escapeHtml(String text) {

        if (text == null) {
            return "";
        }

        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&#39;");
    }
}
