
package ugverse.util;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;

public class UITheme {

    // Main colors
    public static final Color PRIMARY = new Color(29, 78, 216);
    public static final Color DARK_BLUE = new Color(18, 59, 115);
    public static final Color LIGHT_BLUE = new Color(234, 241, 252);
    public static final Color BACKGROUND = new Color(247, 249, 253);
    public static final Color WHITE = Color.WHITE;
    public static final Color TEXT = new Color(31, 41, 55);
    public static final Color MUTED = new Color(100, 116, 139);
    public static final Color BORDER = new Color(220, 230, 245);
    public static final Color DANGER = new Color(220, 38, 38);

    // Fonts
    public static final Font TITLE_FONT =
            new Font("SansSerif", Font.BOLD, 24);

    public static final Font HEADING_FONT =
            new Font("SansSerif", Font.BOLD, 18);

    public static final Font BODY_FONT =
            new Font("SansSerif", Font.PLAIN, 14);

    public static final Font BUTTON_FONT =
            new Font("SansSerif", Font.BOLD, 14);

    // Primary button style
    public static void stylePrimaryButton(JButton button) {
        button.setFont(BUTTON_FONT);
        button.setForeground(WHITE);
        button.setBackground(PRIMARY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBorder(
                BorderFactory.createEmptyBorder(10, 18, 10, 18)
        );
        button.setCursor(
                new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)
        );
    }

    // Secondary button style
    public static void styleSecondaryButton(JButton button) {
        button.setFont(BUTTON_FONT);
        button.setForeground(DARK_BLUE);
        button.setBackground(LIGHT_BLUE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBorder(
                BorderFactory.createEmptyBorder(10, 18, 10, 18)
        );
        button.setCursor(
                new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)
        );
    }

    // Text field style
    public static void styleTextField(JTextField field) {
        field.setFont(BODY_FONT);
        field.setForeground(TEXT);
        field.setBackground(WHITE);
        field.setCaretColor(PRIMARY);
        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        BorderFactory.createEmptyBorder(8, 10, 8, 10)
                )
        );
    }

    // Heading style
    public static void styleHeading(JLabel label) {
        label.setFont(HEADING_FONT);
        label.setForeground(DARK_BLUE);
    }
}
