package pcregistration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterPCForm extends JFrame {
    public RegisterPCForm() {
        setTitle("Register PC");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set up the main panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(255, 255, 255)); // white background for panel
        add(panel);

        // Create GridBagConstraints to control placement of components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components

        // PC ID Label and TextField
        JLabel pcIDLabel = new JLabel("PC ID:");
        pcIDLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(pcIDLabel, gbc);

        JTextField pcIDField = new JTextField(20);
        pcIDField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(pcIDField, gbc);

        // PC Name Label and TextField
        JLabel pcNameLabel = new JLabel("PC Name:");
        pcNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(pcNameLabel, gbc);

        JTextField pcNameField = new JTextField(20);
        pcNameField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(pcNameField, gbc);

        // User ID Label and TextField
        JLabel userIDLabel = new JLabel("User ID:");
        userIDLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(userIDLabel, gbc);

        JTextField userIDField = new JTextField(20);
        userIDField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(userIDField, gbc);

        // PC Model Label and TextField
        JLabel pcModelLabel = new JLabel("PC Model:");
        pcModelLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(pcModelLabel, gbc);

        JTextField pcModelField = new JTextField(20);
        pcModelField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(pcModelField, gbc);

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBackground(new Color(0, 123, 255)); // Blue color
        registerButton.setForeground(Color.WHITE);
        registerButton.setPreferredSize(new Dimension(150, 40));
        registerButton.setFocusPainted(false);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(registerButton, gbc);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(220, 53, 69)); // Red color
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.setFocusPainted(false);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(backButton, gbc);

        // Register button action
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "INSERT INTO pcinformation (pcID, pcName, userID, pcModel, status) VALUES (?, ?, ?, ?, 'Available')";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, pcIDField.getText());
                    preparedStatement.setString(2, pcNameField.getText());
                    preparedStatement.setString(3, userIDField.getText());
                    preparedStatement.setString(4, pcModelField.getText());
                    preparedStatement.executeUpdate();

                    JOptionPane.showMessageDialog(RegisterPCForm.this, "PC registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(RegisterPCForm.this, "Error registering PC!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Back button action
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current form
                setVisible(false); // Navigate back to the Staff Page
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegisterPCForm registerPCForm = new RegisterPCForm();
            registerPCForm.setVisible(true);
        });
    }
}
