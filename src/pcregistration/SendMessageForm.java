package pcregistration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SendMessageForm extends JFrame {
    public SendMessageForm(String userID) {
        setTitle("Send Message");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on screen

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245)); // Light grey background
        add(panel);

        // GridBagConstraints for better control over layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components

        // Create and style labels and input fields
        JLabel nameLabel = new JLabel("Student Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setForeground(new Color(63, 81, 181)); // Blue color
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        JTextField nameField = new JTextField(15);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nameField, gbc);

        JLabel pcIDLabel = new JLabel("PC ID:");
        pcIDLabel.setFont(new Font("Arial", Font.BOLD, 14));
        pcIDLabel.setForeground(new Color(63, 81, 181)); // Blue color
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(pcIDLabel, gbc);

        JTextField pcIDField = new JTextField(15);
        pcIDField.setFont(new Font("Arial", Font.PLAIN, 14));
        pcIDField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(pcIDField, gbc);

        JLabel messageLabel = new JLabel("Message:");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        messageLabel.setForeground(new Color(63, 81, 181)); // Blue color
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(messageLabel, gbc);

        JTextArea messageArea = new JTextArea(4, 20);
        messageArea.setFont(new Font("Arial", Font.PLAIN, 14));
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setPreferredSize(new Dimension(200, 100));
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(scrollPane, gbc);

        // Send button with modern styling
        JButton sendButton = new JButton("Send");
        sendButton.setFont(new Font("Arial", Font.BOLD, 16));
        sendButton.setBackground(new Color(63, 81, 181)); // Blue background
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setPreferredSize(new Dimension(200, 40));
        sendButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding inside the button
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate input fields
                if (nameField.getText().isEmpty() || pcIDField.getText().isEmpty() || messageArea.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(SendMessageForm.this, "All fields must be filled!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Send data to the database
                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "INSERT INTO message (studentName, userID, pcID, message) VALUES (?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, nameField.getText());
                    preparedStatement.setString(2, userID);
                    preparedStatement.setString(3, pcIDField.getText());
                    preparedStatement.setString(4, messageArea.getText());
                    preparedStatement.executeUpdate();

                    JOptionPane.showMessageDialog(SendMessageForm.this, "Message sent successfully!");
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(SendMessageForm.this, "Error sending message!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(sendButton, gbc);
    }

    public static void main(String[] args) {
        new SendMessageForm("student123").setVisible(true);
    }
}
