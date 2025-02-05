package pcregistration;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class displaypcinfo extends JFrame {
    private JTable pcTable;

    public displaypcinfo(String userID) {
        // Set up the JFrame
        setTitle("PC Information");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set a background color for the frame
        getContentPane().setBackground(new Color(245, 245, 245));

        // Create the table with a custom model
        pcTable = new JTable();
        pcTable.setFillsViewportHeight(true);
        pcTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pcTable.setRowHeight(30);
        pcTable.setFont(new Font("Arial", Font.PLAIN, 14));
        pcTable.setBackground(new Color(255, 255, 255));
        pcTable.setGridColor(new Color(200, 200, 200));
        pcTable.setShowGrid(true);
        pcTable.setAutoCreateRowSorter(true);

        // ScrollPane for the table
        JScrollPane scrollPane = new JScrollPane(pcTable);
        scrollPane.setPreferredSize(new Dimension(580, 300));
        add(scrollPane, BorderLayout.CENTER);

        // Panel for the Back button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));
        JButton backButton = createRoundedButton("Back", new Color(38, 166, 154), new Color(255, 255, 255));
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load PC information
        loadPCInformation(userID);

        // Action listener for the Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
                setVisible(false);// Close the current window
            }
        });
    }

    private void loadPCInformation(String userID) {
        // Table model to hold the data
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("PC ID");
        model.addColumn("PC Name");
        model.addColumn("User ID");
        model.addColumn("PC Model");
        model.addColumn("Status");

        // Use DatabaseConnection class to get the connection
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM pcinformation WHERE userID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userID);
            ResultSet resultSet = statement.executeQuery();

            // Populate the table
            while (resultSet.next()) {
                model.addRow(new Object[]{
                    resultSet.getString("pcID"),
                    resultSet.getString("pcName"),
                    resultSet.getString("userID"),
                    resultSet.getString("pcModel"),
                    resultSet.getString("status")
                });
            }

            pcTable.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading PC information: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JButton createRoundedButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setBorder(BorderFactory.createLineBorder(bgColor, 2));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(100, 40));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new displaypcinfo("sampleUserID").setVisible(true));
    }
}
