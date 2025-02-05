package pcregistration;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewMessagesForm extends JFrame {
    
    private DefaultTableModel tableModel;

    public ViewMessagesForm() {
        setTitle("View Messages");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set a custom layout with padding
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20)); // 20px padding between components
        mainPanel.setBackground(new Color(240, 240, 240)); // Light background color
        add(mainPanel);

        // Add a title label at the top
        JLabel titleLabel = new JLabel("Messages from Students", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(60, 60, 60));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Space around text
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Table for displaying messages with improved design
        String[] columnNames = {"Student Name", "User ID", "PC ID", "Message"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        table.setRowHeight(30); // Increase row height for better visibility
        table.setSelectionBackground(new Color(0, 123, 255)); // Change selection color
        table.setSelectionForeground(Color.WHITE);
        table.setFont(new Font("Arial", Font.PLAIN, 14)); // Set table font
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel for the Back button, aligned at the center
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 40)); // Set the button size
        backButton.setBackground(new Color(255, 80, 80)); // Red background
        backButton.setForeground(Color.WHITE); // White text
        backButton.setFont(new Font("Arial", Font.BOLD, 14)); // Bold font
        backButton.setFocusPainted(false); // Remove border on focus
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Load messages from the database
        loadMessagesFromDatabase();

        // Back button action listener
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current form
                setVisible(false); // Hide the current form
                //new StaffPage().setVisible(true); // Optionally navigate back to the staff page
            }
        });
    }

    // Method to load messages from the database and update the table
    private void loadMessagesFromDatabase() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM message";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Clear previous data from the table model
            tableModel.setRowCount(0);

            while (resultSet.next()) {
                String studentName = resultSet.getString("studentName");
                String userID = resultSet.getString("userID");
                String pcID = resultSet.getString("pcID");
                String message = resultSet.getString("message");
                tableModel.addRow(new Object[]{studentName, userID, pcID, message});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching messages!");
        }
    }

    // Call this method to refresh the table
    public void refreshTable() {
        loadMessagesFromDatabase();
    }
}
