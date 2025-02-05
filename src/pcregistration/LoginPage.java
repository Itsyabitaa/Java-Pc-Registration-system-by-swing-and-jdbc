package pcregistration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginPage extends JFrame {
    private JTextField userIDField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginPage() {
        setTitle("Login Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Background color
        getContentPane().setBackground(new Color(245, 245, 245)); // light gray

        // Panel to hold all login components
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255)); // white background for the panel
        panel.setLayout(new GridBagLayout()); // GridBagLayout for better alignment
        add(panel, BorderLayout.CENTER);

        // Constraints for GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components
        gbc.gridx = 0;
        gbc.gridy = 0;

        // User ID Label
        JLabel userIDLabel = new JLabel("User ID:");
        userIDLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(userIDLabel, gbc);

        gbc.gridx = 1;
        userIDField = new JTextField(20);
        userIDField.setFont(new Font("Arial", Font.PLAIN, 14));
        userIDField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        panel.add(userIDField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(0, 123, 255)); // Blue color
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        loginButton.setPreferredSize(new Dimension(100, 40));

        panel.add(loginButton, gbc);

        // Action listener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        // Create and add a "Forgot Password?" label
       
    }

    private void handleLogin() {
        String userID = userIDField.getText();
        String password = new String(passwordField.getPassword());

        if (userID.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter User ID and Password.");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE userID = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userID);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String role = resultSet.getString("role");

                if (role.equalsIgnoreCase("student")) {
                    StudentPage studentPage = new StudentPage(userID);
                    studentPage.setVisible(true);
                    dispose();
                } else if (role.equalsIgnoreCase("staff")) {
                    StaffPage staffPage = new StaffPage();
                    staffPage.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid user role.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid User ID or Password.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database.");
        }
    }

    public static void main(String[] args) {
        LoginPage loginPage = new LoginPage();
        loginPage.setVisible(true);
    }
}
