package pcregistration;

import javax.swing.*;
import java.awt.*;

public class StudentPage extends JFrame {
    private String userID;

    public StudentPage(String userID) {
        this.userID = userID;

        // Set window properties
        setTitle("Student Page");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window on screen
        setLayout(new BorderLayout());

        // Background color
        getContentPane().setBackground(new Color(245, 245, 245)); // light gray

        // Panel to hold all buttons
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255)); // white background for the panel
        panel.setLayout(new GridBagLayout()); // GridBagLayout for better alignment
        add(panel, BorderLayout.CENTER);

        // Constraints for GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Display PC Info button
        JButton displayPCButton = new JButton("Display PC Info");
        displayPCButton.setFont(new Font("Arial", Font.BOLD, 14));
        displayPCButton.setBackground(new Color(0, 123, 255)); // Blue color
        displayPCButton.setForeground(Color.WHITE);
        displayPCButton.setFocusPainted(false);
        displayPCButton.setPreferredSize(new Dimension(200, 40));
        panel.add(displayPCButton, gbc);

        gbc.gridy++;
        // Send Message button
        JButton sendMessageButton = new JButton("Send Message");
        sendMessageButton.setFont(new Font("Arial", Font.BOLD, 14));
        sendMessageButton.setBackground(new Color(0, 123, 255)); // Blue color
        sendMessageButton.setForeground(Color.WHITE);
        sendMessageButton.setFocusPainted(false);
        sendMessageButton.setPreferredSize(new Dimension(200, 40));
        panel.add(sendMessageButton, gbc);

        gbc.gridy++;
        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setBackground(new Color(220, 53, 69)); // Red color
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setPreferredSize(new Dimension(200, 40));
        panel.add(logoutButton, gbc);

        // Action listeners for each button
        sendMessageButton.addActionListener(e -> {
            SendMessageForm sendMessageForm = new SendMessageForm(userID);
            sendMessageForm.setSize(600, 600); // Set the size to 600x600
            sendMessageForm.setLocationRelativeTo(null); // Center the form on the screen
            sendMessageForm.setVisible(true);
        });

        displayPCButton.addActionListener(e -> {
            displaypcinfo displayPCInfoForm = new displaypcinfo(userID);
            displayPCInfoForm.setSize(600, 600); // Set the size to 600x600
            displayPCInfoForm.setLocationRelativeTo(null); // Center the form on the screen
            displayPCInfoForm.setVisible(true);
        });

        // Action listener for the logout button
        logoutButton.addActionListener(e -> {
            dispose(); // Close the current StudentPage window
            new LoginPage().setVisible(true); // Navigate to LoginPage
        });
    }

    public static void main(String[] args) {
        // Test the StudentPage with a dummy userID
        new StudentPage("student123").setVisible(true);
    }
}
