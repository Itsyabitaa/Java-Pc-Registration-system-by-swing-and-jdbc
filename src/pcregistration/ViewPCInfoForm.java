package pcregistration;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ViewPCInfoForm extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton resetButton;
    private JButton backButton;
    private JTextField searchField;
    private JButton searchButton;

    public ViewPCInfoForm() {
        setTitle("View and Manage PC Information");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Set a custom font for the window title
        Font titleFont = new Font("Arial", Font.BOLD, 24);
        JLabel titleLabel = new JLabel("PC Information", JLabel.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(new Color(0, 102, 204));

        // Table setup
        String[] columnNames = {"PC ID", "PC Name", "User ID", "PC Model", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // Allow editing all columns except the PC ID
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(0, 153, 255));
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Load data into the table
        loadPCInformation();

        // Top Panel for Back and Search
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(240, 240, 240));
        
        backButton = new JButton("Back");
        backButton.setBackground(new Color(255, 102, 102));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(e -> {
            // Code to navigate back to Staff Page
            dispose();
            setVisible(false); // Assuming you have a StaffPage class
        });

        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(240, 240, 240));
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        resetButton = new JButton("Reset");

        // Styling buttons
        searchButton.setBackground(new Color(0, 204, 102));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        resetButton.setBackground(new Color(255, 153, 51));
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(resetButton);
        
        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // Bottom Panel for Update and Delete
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));

        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        // Styling buttons
        updateButton.setBackground(new Color(51, 153, 255));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);
        deleteButton.setBackground(new Color(255, 51, 51));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);

        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Button functionalities
        searchButton.addActionListener(e -> searchPCInformation());
        resetButton.addActionListener(e -> resetTableData());
        updateButton.addActionListener(e -> updateSelectedRow());
        deleteButton.addActionListener(e -> deleteSelectedRow());
    }

    private void loadPCInformation() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM pcinformation";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String pcID = resultSet.getString("pcID");
                String pcName = resultSet.getString("pcName");
                String userID = resultSet.getString("userID");
                String pcModel = resultSet.getString("pcModel");
                String status = resultSet.getString("status");
                tableModel.addRow(new Object[]{pcID, pcName, userID, pcModel, status});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching PC information!");
        }
    }

    private void searchPCInformation() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search term.");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM pcinformation WHERE pcID LIKE ? OR pcName LIKE ? OR userID LIKE ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + searchText + "%");
            statement.setString(2, "%" + searchText + "%");
            statement.setString(3, "%" + searchText + "%");
            ResultSet resultSet = statement.executeQuery();

            tableModel.setRowCount(0); // Clear table data
            while (resultSet.next()) {
                String pcID = resultSet.getString("pcID");
                String pcName = resultSet.getString("pcName");
                String userID = resultSet.getString("userID");
                String pcModel = resultSet.getString("pcModel");
                String status = resultSet.getString("status");
                tableModel.addRow(new Object[]{pcID, pcName, userID, pcModel, status});
            }

            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No records found matching the search term.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching PC information!");
        }
    }

    private void resetTableData() {
        searchField.setText("");
        tableModel.setRowCount(0); // Clear existing data
        loadPCInformation(); // Reload all data
    }

    private void updateSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to update.");
            return;
        }

        String pcID = (String) tableModel.getValueAt(selectedRow, 0);
        String pcName = JOptionPane.showInputDialog(this, "Enter new PC Name:", tableModel.getValueAt(selectedRow, 1));
        String userID = JOptionPane.showInputDialog(this, "Enter new User ID:", tableModel.getValueAt(selectedRow, 2));
        String pcModel = JOptionPane.showInputDialog(this, "Enter new PC Model:", tableModel.getValueAt(selectedRow, 3));
        String status = JOptionPane.showInputDialog(this, "Enter new Status:", tableModel.getValueAt(selectedRow, 4));

        if (pcName == null || userID == null || pcModel == null || status == null) {
            JOptionPane.showMessageDialog(this, "Update canceled.");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE pcinformation SET pcName = ?, userID = ?, pcModel = ?, status = ? WHERE pcID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, pcName);
            statement.setString(2, userID);
            statement.setString(3, pcModel);
            statement.setString(4, status);
            statement.setString(5, pcID);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                tableModel.setValueAt(pcName, selectedRow, 1);
                tableModel.setValueAt(userID, selectedRow, 2);
                tableModel.setValueAt(pcModel, selectedRow, 3);
                tableModel.setValueAt(status, selectedRow, 4);
                JOptionPane.showMessageDialog(this, "Row updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No rows were updated.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating row!");
        }
    }

    private void deleteSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.");
            return;
        }

        String pcID = (String) tableModel.getValueAt(selectedRow, 0);

        int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this row?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM pcinformation WHERE pcID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, pcID);
            statement.executeUpdate();

            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Row deleted successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting row!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ViewPCInfoForm().setVisible(true);
        });
    }
}
