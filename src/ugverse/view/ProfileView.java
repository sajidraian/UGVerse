
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ugverse.controller.ProfileController;
import ugverse.model.ProfileModel;
import ugverse.util.UITheme;

public class ProfileView extends JFrame {

    private final int userId;
    private final String name;
    private final String role;

    private final ProfileController controller;

    private final JTextField departmentField;
    private final JTextField batchField;
    private final JTextField phoneField;
    private final JTextArea bioArea;

    private final JButton saveButton;

    public ProfileView(int userId, String name, String role) {

        this.userId = userId;
        this.name = name;
        this.role = role;

        controller = new ProfileController();

        setTitle("UGVerse | My Profile");
        setSize(760, 650);
        setMinimumSize(new Dimension(600, 550));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main background
        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(UITheme.BACKGROUND);

        // =========================
        // PROFILE HEADER
        // =========================
        JPanel header = new JPanel(new BorderLayout(15, 0));
        header.setBackground(UITheme.DARK_BLUE);
        header.setBorder(
                BorderFactory.createEmptyBorder(22, 30, 22, 30)
        );

        JPanel headerText = new JPanel();
        headerText.setLayout(
                new javax.swing.BoxLayout(
                        headerText, javax.swing.BoxLayout.Y_AXIS
                )
        );
        headerText.setOpaque(false);

        JLabel title = new JLabel("My Profile");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
                "Manage your academic and personal information"
        );
        subtitle.setFont(UITheme.BODY_FONT);
        subtitle.setForeground(new Color(220, 235, 255));

        headerText.add(title);
        headerText.add(javax.swing.Box.createVerticalStrut(6));
        headerText.add(subtitle);

        JLabel avatar = new JLabel(getInitials(name), SwingConstants.CENTER);
        avatar.setFont(new Font("SansSerif", Font.BOLD, 20));
        avatar.setForeground(UITheme.DARK_BLUE);
        avatar.setOpaque(true);
        avatar.setBackground(Color.WHITE);
        avatar.setPreferredSize(new Dimension(54, 54));
        avatar.setBorder(
                BorderFactory.createLineBorder(
                        new Color(210, 225, 250), 2
                )
        );

        header.add(headerText, BorderLayout.CENTER);
        header.add(avatar, BorderLayout.EAST);

        // =========================
        // USER INFORMATION CARD
        // =========================
        JPanel page = new JPanel(new BorderLayout(0, 18));
        page.setBackground(UITheme.BACKGROUND);
        page.setBorder(
                BorderFactory.createEmptyBorder(22, 30, 25, 30)
        );

        JPanel userCard = new JPanel(new BorderLayout(15, 0));
        userCard.setBackground(Color.WHITE);
        userCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UITheme.BORDER),
                        BorderFactory.createEmptyBorder(15, 18, 15, 18)
                )
        );

        JPanel identity = new JPanel();
        identity.setLayout(
                new javax.swing.BoxLayout(
                        identity, javax.swing.BoxLayout.Y_AXIS
                )
        );
        identity.setOpaque(false);

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(UITheme.HEADING_FONT);
        nameLabel.setForeground(UITheme.DARK_BLUE);

        JLabel roleLabel = new JLabel(role + " • UGVerse Member");
        roleLabel.setFont(UITheme.BODY_FONT);
        roleLabel.setForeground(UITheme.MUTED);

        identity.add(nameLabel);
        identity.add(javax.swing.Box.createVerticalStrut(5));
        identity.add(roleLabel);

        JLabel idLabel = new JLabel("ID: " + userId);
        idLabel.setFont(UITheme.BODY_FONT);
        idLabel.setForeground(UITheme.MUTED);

        userCard.add(identity, BorderLayout.CENTER);
        userCard.add(idLabel, BorderLayout.EAST);

        // =========================
        // PROFILE FORM
        // =========================
        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UITheme.BORDER),
                        BorderFactory.createEmptyBorder(22, 22, 22, 22)
                )
        );

        departmentField = new JTextField(22);
        batchField = new JTextField(22);
        phoneField = new JTextField(22);

        bioArea = new JTextArea(4, 22);
        bioArea.setFont(UITheme.BODY_FONT);
        bioArea.setForeground(UITheme.TEXT);
        bioArea.setBackground(Color.WHITE);
        bioArea.setLineWrap(true);
        bioArea.setWrapStyleWord(true);
        bioArea.setCaretColor(UITheme.PRIMARY);
        bioArea.setBorder(
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        );

        UITheme.styleTextField(departmentField);
        UITheme.styleTextField(batchField);
        UITheme.styleTextField(phoneField);

        JLabel formTitle = new JLabel("Academic & Personal Details");
        formTitle.setFont(UITheme.HEADING_FONT);
        formTitle.setForeground(UITheme.DARK_BLUE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 18, 0);
        formCard.add(formTitle, gbc);

        addFormRow(formCard, gbc, 1,
                "Department *", departmentField);

        addFormRow(formCard, gbc, 3,
                "Batch", batchField);

        addFormRow(formCard, gbc, 5,
                "Phone", phoneField);

        JLabel bioLabel = createFieldLabel("Bio");
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(12, 0, 7, 0);
        formCard.add(bioLabel, gbc);

        JScrollPane bioScroll = new JScrollPane(bioArea);
        bioScroll.setBorder(
                BorderFactory.createLineBorder(UITheme.BORDER)
        );
        bioScroll.setPreferredSize(new Dimension(300, 95));

        gbc.gridy = 8;
        gbc.insets = new Insets(0, 0, 0, 0);
        formCard.add(bioScroll, gbc);

        // =========================
        // ACTION BUTTONS
        // =========================
        saveButton = new JButton("Save Profile");
        UITheme.stylePrimaryButton(saveButton);

        JButton closeButton = new JButton("Close");
        UITheme.styleSecondaryButton(closeButton);

        JPanel actions = new JPanel(new BorderLayout());
        actions.setOpaque(false);

        JLabel requiredNote = new JLabel("* Required field");
        requiredNote.setFont(new Font("SansSerif", Font.PLAIN, 12));
        requiredNote.setForeground(UITheme.MUTED);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(closeButton);
        buttonPanel.add(saveButton);

        actions.add(requiredNote, BorderLayout.WEST);
        actions.add(buttonPanel, BorderLayout.EAST);

        JPanel formArea = new JPanel(new BorderLayout(0, 15));
        formArea.setOpaque(false);
        formArea.add(formCard, BorderLayout.CENTER);
        formArea.add(actions, BorderLayout.SOUTH);

        page.add(userCard, BorderLayout.NORTH);
        page.add(formArea, BorderLayout.CENTER);

        root.add(header, BorderLayout.NORTH);
        root.add(page, BorderLayout.CENTER);

        setContentPane(root);

        // Preserve existing behaviour
        saveButton.addActionListener(e -> save());

        closeButton.addActionListener(e -> dispose());

        load();
    }

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String labelText,
            JTextField field) {

        JLabel label = createFieldLabel(labelText);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 7, 0);
        panel.add(label, gbc);

        gbc.gridy = row + 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(field, gbc);
    }

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UITheme.BUTTON_FONT);
        label.setForeground(UITheme.TEXT);
        return label;
    }

    private String getInitials(String fullName) {

        if (fullName == null || fullName.isBlank()) {
            return "U";
        }

        String[] parts = fullName.trim().split("\\s+");

        if (parts.length == 1) {
            return parts[0].substring(0, 1).toUpperCase();
        }

        return (
                parts[0].substring(0, 1)
                + parts[parts.length - 1].substring(0, 1)
        ).toUpperCase();
    }

    private void load() {
        try {
            ProfileModel profile = controller.getProfile(userId);

            departmentField.setText(profile.getDepartment());
            batchField.setText(profile.getBatch());
            phoneField.setText(profile.getPhone());
            bioArea.setText(profile.getBio());

        } catch (SQLException ex) {
            showError(ex);
        }
    }

    private void save() {

        saveButton.setEnabled(false);

        try {
            boolean saved = controller.saveProfile(
                    userId,
                    departmentField.getText(),
                    batchField.getText(),
                    bioArea.getText(),
                    phoneField.getText()
            );

            if (saved) {
                JOptionPane.showMessageDialog(
                        this,
                        "Your profile has been saved successfully!",
                        "Profile Updated",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Department is required.",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE
                );
                departmentField.requestFocusInWindow();
            }

        } catch (SQLException ex) {
            showError(ex);

        } finally {
            saveButton.setEnabled(true);
        }
    }

    private void showError(Exception ex) {
        ex.printStackTrace();

        JOptionPane.showMessageDialog(
                this,
                "Profile operation failed. Please check the database.",
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
