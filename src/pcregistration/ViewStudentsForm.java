package pcregistration;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ViewStudentsForm extends JFrame {
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public ViewStudentsForm() {
        setTitle("View Students");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Set a nice background color
        getContentPane().setBackground(new Color(245, 245, 245)); // Light gray background
        setLayout(new BorderLayout(10, 10));

        // Table setup with custom font and row height for a clean look
        String[] columnNames = {"User ID", "Username", "Password", "Role"};
        tableModel = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(tableModel);
        studentTable.setFont(new Font("Arial", Font.PLAIN, 14));
        studentTable.setRowHeight(30); // Increase row height for better readability
        studentTable.setSelectionBackground(new Color(0, 123, 255)); // Highlight selected row
        studentTable.setSelectionForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        add(scrollPane, BorderLayout.CENTER);

        // Search panel with custom layout and styling
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(245, 245, 245));
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 14));
        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setBackground(new Color(0, 123, 255));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        JButton resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.BOLD, 14));
        resetButton.setBackground(new Color(220, 53, 69));
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(resetButton);
        add(searchPanel, BorderLayout.NORTH);

        // Button panel setup with consistent styling
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton backButton = new JButton("Back");
        updateButton.setFont(new Font("Arial", Font.BOLD, 14));
        updateButton.setBackground(new Color(40, 167, 69));
        updateButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(108, 117, 125));
        backButton.setForeground(Color.WHITE);

        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loadStudents();

        // Search Button Logic
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText().trim();
                if (!keyword.isEmpty()) {
                    searchStudents(keyword);
                } else {
                    JOptionPane.showMessageDialog(ViewStudentsForm.this, "Please enter a keyword to search!");
                }
            }
        });

        // Reset Button Logic
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchField.setText("");
                loadStudents();
            }
        });

        // Update Button Logic
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(ViewStudentsForm.this, "Please select a row to update.");
                    return;
                }
                String userID = (String) tableModel.getValueAt(selectedRow, 0);
                String username = (String) tableModel.getValueAt(selectedRow, 1);
                String password = (String) tableModel.getValueAt(selectedRow, 2);

                String newUsername = JOptionPane.showInputDialog(ViewStudentsForm.this, "Enter new username:", username);
                String newPassword = JOptionPane.showInputDialog(ViewStudentsForm.this, "Enter new password:", password);

                if (newUsername != null && newPassword != null) {
                    try (Connection connection = DatabaseConnection.getConnection()) {
                        String query = "UPDATE users SET username = ?, password = ? WHERE userID = ?";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1, newUsername);
                        statement.setString(2, newPassword);
                        statement.setString(3, userID);
                        statement.executeUpdate();
                        JOptionPane.showMessageDialog(ViewStudentsForm.this, "Student updated successfully!");
                        loadStudents();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(ViewStudentsForm.this, "Error updating student!");
                    }
                }
            }
        });

        // Delete Button Logic
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(ViewStudentsForm.this, "Please select a row to delete.");
                    return;
                }
                String userID = (String) tableModel.getValueAt(selectedRow, 0);

                int confirm = JOptionPane.showConfirmDialog(ViewStudentsForm.this, "Are you sure you want to delete this student?");
                if (confirm == JOptionPane.YES_OPTION) {
                    try (Connection connection = DatabaseConnection.getConnection()) {
                        String query = "DELETE FROM users WHERE userID = ?";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1, userID);
                        statement.executeUpdate();
                        JOptionPane.showMessageDialog(ViewStudentsForm.this, "Student deleted successfully!");
                        loadStudents();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(ViewStudentsForm.this, "Error deleting student!");
                    }
                }
            }
        });

        // Back Button Logic
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current frame
                setVisible(false); // Open the staff page
            }
        });
    }

    private void loadStudents() {
        tableModel.setRowCount(0);
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE role = 'student'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String userID = resultSet.getString("userID");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                tableModel.addRow(new Object[]{userID, username, password, role});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading student data!");
        }
    }

    private void searchStudents(String keyword) {
        tableModel.setRowCount(0);
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE role = 'student' AND (username LIKE ? OR userID LIKE ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + keyword + "%");
            statement.setString(2, "%" + keyword + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String userID = resultSet.getString("userID");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                tableModel.addRow(new Object[]{userID, username, password, role});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching student data!");
        }
    }
}
