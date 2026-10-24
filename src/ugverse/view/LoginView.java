
package ugverse.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Box;
import javax.swing.BoxLayout;

import ugverse.controller.LoginController;
import ugverse.model.LoginModel;
import ugverse.util.UITheme;

public class LoginView extends JFrame {

    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final JButton loginButton;

    private final LoginController controller;

    public LoginView() {

        controller = new LoginController();

        setTitle("UGVerse | Sign In");
        setSize(850, 520);
        setMinimumSize(new Dimension(700, 460));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UITheme.WHITE);

        // =========================
        // LEFT BRANDING PANEL
        // =========================
        JPanel brandingPanel = new JPanel();
        brandingPanel.setLayout(
                new BoxLayout(brandingPanel, BoxLayout.Y_AXIS)
        );
        brandingPanel.setBackground(UITheme.DARK_BLUE);
        brandingPanel.setPreferredSize(new Dimension(330, 520));
        brandingPanel.setBorder(
                BorderFactory.createEmptyBorder(55, 30, 35, 30)
        );

        JLabel logo = new JLabel("UGVerse");
        logo.setFont(new Font("SansSerif", Font.BOLD, 32));
        logo.setForeground(UITheme.WHITE);
        logo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel tagline = new JLabel(
                "<html><div style='text-align:center;'>"
                + "Your University,<br>Your Community."
                + "</div></html>"
        );
        tagline.setFont(new Font("SansSerif", Font.PLAIN, 19));
        tagline.setForeground(new Color(220, 235, 255));
        tagline.setAlignmentX(CENTER_ALIGNMENT);

        JLabel description = new JLabel(
                "<html><div style='text-align:center; width:230px;'>"
                + "Connect with students, faculty and alumni. "
                + "Build meaningful academic and professional "
                + "connections in one place."
                + "</div></html>"
        );
        description.setFont(UITheme.BODY_FONT);
        description.setForeground(new Color(220, 235, 255));
        description.setAlignmentX(CENTER_ALIGNMENT);

        brandingPanel.add(Box.createVerticalGlue());
        brandingPanel.add(logo);
        brandingPanel.add(Box.createVerticalStrut(22));
        brandingPanel.add(tagline);
        brandingPanel.add(Box.createVerticalStrut(18));
        brandingPanel.add(description);
        brandingPanel.add(Box.createVerticalGlue());

        // =========================
        // RIGHT LOGIN PANEL
        // =========================
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(UITheme.WHITE);
        loginPanel.setBorder(
                BorderFactory.createEmptyBorder(35, 45, 35, 45)
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(7, 0, 7, 0);

        JLabel heading = new JLabel("Welcome back!");
        heading.setFont(UITheme.TITLE_FONT);
        heading.setForeground(UITheme.DARK_BLUE);

        JLabel subtitle = new JLabel(
                "Sign in to continue to UGVerse."
        );
        subtitle.setFont(UITheme.BODY_FONT);
        subtitle.setForeground(UITheme.MUTED);

        JLabel emailLabel = new JLabel("Email address");
        emailLabel.setFont(UITheme.BUTTON_FONT);
        emailLabel.setForeground(UITheme.TEXT);

        emailField = new JTextField(22);
        UITheme.styleTextField(emailField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(UITheme.BUTTON_FONT);
        passwordLabel.setForeground(UITheme.TEXT);

        passwordField = new JPasswordField(22);
        UITheme.styleTextField(passwordField);

        loginButton = new JButton("Sign In");
        UITheme.stylePrimaryButton(loginButton);
        loginButton.setPreferredSize(new Dimension(200, 44));

        JLabel footer = new JLabel(
                "UGVerse • Academic & Professional Network",
                SwingConstants.CENTER
        );
        footer.setFont(new Font("SansSerif", Font.PLAIN, 11));
        footer.setForeground(UITheme.MUTED);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 5, 0);
        loginPanel.add(heading, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 25, 0);
        loginPanel.add(subtitle, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(6, 0, 5, 0);
        loginPanel.add(emailLabel, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 12, 0);
        loginPanel.add(emailField, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(6, 0, 5, 0);
        loginPanel.add(passwordLabel, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 20, 0);
        loginPanel.add(passwordField, gbc);

        gbc.gridy = 6;
        gbc.insets = new Insets(5, 0, 20, 0);
        loginPanel.add(loginButton, gbc);

        gbc.gridy = 7;
        gbc.insets = new Insets(5, 0, 0, 0);
        loginPanel.add(footer, gbc);

        // Assemble screen
        mainPanel.add(brandingPanel, BorderLayout.WEST);
        mainPanel.add(loginPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);

        // Existing login behaviour
        loginButton.addActionListener(e -> handleLogin());
        passwordField.addActionListener(e -> handleLogin());
        emailField.addActionListener(e -> passwordField.requestFocusInWindow());
    }

    private void handleLogin() {

        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter both email and password.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        loginButton.setEnabled(false);

        try {
            LoginModel user = controller.authenticate(email, password);

            if (user != null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Welcome, " + user.getName()
                        + "!\nRole: " + user.getRole(),
                        "Login Successful",
                        JOptionPane.INFORMATION_MESSAGE
                );

                new DashboardView(
                        user.getUserId(),
                        user.getName(),
                        user.getRole()
                ).setVisible(true);

                dispose();

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid email or password.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
                passwordField.setText("");
                passwordField.requestFocusInWindow();
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to connect to the database.\n"
                    + "Please check the MySQL server.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            ex.printStackTrace();

        } finally {
            loginButton.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginView().setVisible(true);
        });
    }
}
