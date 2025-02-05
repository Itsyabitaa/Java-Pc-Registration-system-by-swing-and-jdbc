package pcregistration;

import javax.swing.*;
import java.awt.*;

public class StaffPage extends JFrame {
    public StaffPage() {
        setTitle("Staff Page");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        // Register PC button
        JButton registerPCButton = new JButton("Register PC");
        registerPCButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerPCButton.setBackground(new Color(0, 123, 255)); // Blue color
        registerPCButton.setForeground(Color.WHITE);
        registerPCButton.setFocusPainted(false);
        registerPCButton.setPreferredSize(new Dimension(200, 40));
        panel.add(registerPCButton, gbc);

        gbc.gridy++;
        // View Messages button
        JButton viewMessagesButton = new JButton("View Messages");
        viewMessagesButton.setFont(new Font("Arial", Font.BOLD, 14));
        viewMessagesButton.setBackground(new Color(0, 123, 255)); // Blue color
        viewMessagesButton.setForeground(Color.WHITE);
        viewMessagesButton.setFocusPainted(false);
        viewMessagesButton.setPreferredSize(new Dimension(200, 40));
        panel.add(viewMessagesButton, gbc);

        gbc.gridy++;
        // Add User button
        JButton addStudentButton = new JButton("Add User");
        addStudentButton.setFont(new Font("Arial", Font.BOLD, 14));
        addStudentButton.setBackground(new Color(0, 123, 255)); // Blue color
        addStudentButton.setForeground(Color.WHITE);
        addStudentButton.setFocusPainted(false);
        addStudentButton.setPreferredSize(new Dimension(200, 40));
        panel.add(addStudentButton, gbc);

        gbc.gridy++;
        // View Students button
        JButton viewStudentsButton = new JButton("View Students");
        viewStudentsButton.setFont(new Font("Arial", Font.BOLD, 14));
        viewStudentsButton.setBackground(new Color(0, 123, 255)); // Blue color
        viewStudentsButton.setForeground(Color.WHITE);
        viewStudentsButton.setFocusPainted(false);
        viewStudentsButton.setPreferredSize(new Dimension(200, 40));
        panel.add(viewStudentsButton, gbc);

        gbc.gridy++;
        // View PC Info button
        JButton viewPCInfoButton = new JButton("View PC Info");
        viewPCInfoButton.setFont(new Font("Arial", Font.BOLD, 14));
        viewPCInfoButton.setBackground(new Color(0, 123, 255)); // Blue color
        viewPCInfoButton.setForeground(Color.WHITE);
        viewPCInfoButton.setFocusPainted(false);
        viewPCInfoButton.setPreferredSize(new Dimension(200, 40));
        panel.add(viewPCInfoButton, gbc);

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
        registerPCButton.addActionListener(e -> new RegisterPCForm().setVisible(true));
        viewMessagesButton.addActionListener(e -> new ViewMessagesForm().setVisible(true));
        addStudentButton.addActionListener(e -> new AddStudentForm().setVisible(true));
        viewStudentsButton.addActionListener(e -> new ViewStudentsForm().setVisible(true));
        viewPCInfoButton.addActionListener(e -> new ViewPCInfoForm().setVisible(true));

        // Action listener for the logout button
        logoutButton.addActionListener(e -> {
            setVisible(false); // Hide the current frame (StaffPage)
            new LoginPage().setVisible(true); // Show the login page
            dispose(); // Dispose of the current frame to free resources
        });
    }

    public static void main(String[] args) {
        StaffPage staffPage = new StaffPage();
        staffPage.setVisible(true);
    }
}
