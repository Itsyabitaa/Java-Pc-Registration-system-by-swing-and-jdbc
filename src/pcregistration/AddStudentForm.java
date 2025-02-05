package pcregistration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddStudentForm extends JFrame {
    public AddStudentForm() {
        setTitle("Add User");
        setSize(400, 400); // Adjust the size to fit new components
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on the screen

        // Set a modern, clean layout with padding
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // Use GridBagLayout for better control
        panel.setBackground(new Color(255, 255, 255)); // White background for cleanliness
        add(panel);

        // GridBagConstraints for better control over component placement
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make the components stretch horizontally

        // Labels and Text Fields
        JLabel userIDLabel = new JLabel("User ID:");
        userIDLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userIDLabel, gbc);

        JTextField userIDField = new JTextField();
        userIDField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(userIDField, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(roleLabel, gbc);

        // Dropdown (combo box) for selecting role (Staff or Student)
        String[] roles = {"Student", "Staff"};
        JComboBox<String> roleComboBox = new JComboBox<>(roles);
        roleComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(roleComboBox, gbc);

        // Button Panel for the Add and Back buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center the buttons
        buttonPanel.setBackground(new Color(255, 255, 255));

        JButton addButton = new JButton("Add");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(new Color(0, 123, 255)); // Blue background
        addButton.setPreferredSize(new Dimension(100, 40));
        addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        buttonPanel.add(addButton);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(255, 80, 80)); // Red background
        backButton.setPreferredSize(new Dimension(100, 40));
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        buttonPanel.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        // Add action listener for the Add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "INSERT INTO users (userID, username, password, role) VALUES (?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, userIDField.getText());
                    preparedStatement.setString(2, usernameField.getText());
                    preparedStatement.setString(3, new String(passwordField.getPassword()));
                    preparedStatement.setString(4, roleComboBox.getSelectedItem().toString()); // Get selected role
                    preparedStatement.executeUpdate();

                    JOptionPane.showMessageDialog(AddStudentForm.this, "User added successfully!");
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AddStudentForm.this, "Error adding user!");
                }
            }
        });

        // Add action listener for the Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current frame
                setVisible(false); // Optionally navigate back to another page
            }
        });
    }
}
