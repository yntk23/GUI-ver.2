import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class DataWeek7 {
    JFrame frame;
    private JTextField keywordField;
    private JButton searchButton;
    private JButton insertButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTable resultTable;
    private DefaultTableModel tableModel;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                DataWeek7 window = new DataWeek7();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public DataWeek7() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        keywordField = new JTextField();
        keywordField.setBounds(10, 10, 200, 30);
        frame.getContentPane().add(keywordField);

        searchButton = new JButton("Search");
        searchButton.setBounds(220, 10, 100, 30);
        frame.getContentPane().add(searchButton);

        insertButton = new JButton("Insert");
        insertButton.setBounds(330, 10, 100, 30);
        frame.getContentPane().add(insertButton);

        updateButton = new JButton("Update");
        updateButton.setBounds(330, 50, 100, 30);
        frame.getContentPane().add(updateButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(330, 90, 100, 30);
        frame.getContentPane().add(deleteButton);

        resultTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBounds(10, 50, 300, 300);
        frame.getContentPane().add(scrollPane);

        // Initialize table model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Firstname");
        tableModel.addColumn("Lastname");
        tableModel.addColumn("StudentID");
        tableModel.addColumn("Major");
        resultTable.setModel(tableModel);

        // ActionListener for Search Button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/dataweek7?user=root&password=");
                    String sql = "SELECT * FROM cai WHERE Firstname LIKE ? OR Lastname LIKE ? OR StudentID LIKE ? OR Major LIKE ?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, "%" + keywordField.getText() + "%");
                    ps.setString(2, "%" + keywordField.getText() + "%");
                    ps.setString(3, "%" + keywordField.getText() + "%");
                    ps.setString(4, "%" + keywordField.getText() + "%");

                    ResultSet rec = ps.executeQuery();

                    tableModel.setRowCount(0);

                    while (rec.next()) {
                        String firstname = rec.getString("Firstname");
                        String lastname = rec.getString("Lastname");
                        String studentid = rec.getString("StudentID");
                        String major = rec.getString("Major");

                        tableModel.addRow(new Object[]{firstname, lastname, studentid, major});
                    }

                    rec.close();
                    ps.close();
                    conn.close();
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // ActionListener for Insert Button
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/dataweek7?user=root&password=");
                    String sql = "INSERT INTO cai (Firstname, Lastname, StudentID, Major) VALUES (?, ?, ?, ?)";
                    PreparedStatement ps = conn.prepareStatement(sql);

                    String firstname = JOptionPane.showInputDialog("Enter Firstname");
                    String lastname = JOptionPane.showInputDialog("Enter Lastname");
                    String studentID = JOptionPane.showInputDialog("Enter StudentID");
                    String major = JOptionPane.showInputDialog("Enter Major");

                    ps.setString(1, firstname);
                    ps.setString(2, lastname);
                    ps.setString(3, studentID);
                    ps.setString(4, major);

                    int rowsInserted = ps.executeUpdate();

                    if (rowsInserted > 0) {
                        System.out.println("Data inserted successfully!");
                    }

                    ps.close();
                    conn.close();
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // ActionListener for Update Button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = resultTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String firstname = (String) resultTable.getValueAt(selectedRow, 0);
                    String lastname = (String) resultTable.getValueAt(selectedRow, 1);
                    String studentID = (String) resultTable.getValueAt(selectedRow, 2);
                    String major = (String) resultTable.getValueAt(selectedRow, 3);
                    openUpdateDialog(firstname, lastname, studentID, major);
                }
            }
        });

        // ActionListener for Delete Button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = resultTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String studentID = (String) resultTable.getValueAt(selectedRow, 2);
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/dataweek7?user=root&password=");
                        String sql = "DELETE FROM cai WHERE StudentID = ?";
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ps.setString(1, studentID);

                        int rowsDeleted = ps.executeUpdate();

                        if (rowsDeleted > 0) {
                            System.out.println("Data deleted successfully!");
                            tableModel.removeRow(selectedRow);
                        }

                        ps.close();
                        conn.close();
                    } catch (SQLException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void openUpdateDialog(String firstname, String lastname, String studentID, String major) {
        JDialog updateDialog = new JDialog(frame, "Update Data", true);
        updateDialog.setLayout(new GridLayout(5, 2));

        JTextField firstnameField = new JTextField(firstname);
        JTextField lastnameField = new JTextField(lastname);
        JTextField studentIDField = new JTextField(studentID);
        JTextField majorField = new JTextField(major);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String updatedFirstname = firstnameField.getText();
                String updatedLastname = lastnameField.getText();
                String updatedMajor = majorField.getText();
                String updatedStudentID = studentIDField.getText();

                if (!updatedFirstname.isEmpty() && !updatedLastname.isEmpty() && !updatedMajor.isEmpty() && !updatedStudentID.isEmpty()) {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/dataweek7?user=root&password=");
                        
                        // SQL statement for updating data
                        String sql = "UPDATE cai SET Firstname = ?, Lastname = ?, Major = ? WHERE StudentID = ?";
                        PreparedStatement ps = conn.prepareStatement(sql);

                        // Set parameters in PreparedStatement
                        ps.setString(1, updatedFirstname);
                        ps.setString(2, updatedLastname);
                        ps.setString(3, updatedMajor);
                        ps.setString(4, updatedStudentID);

                        int rowsUpdated = ps.executeUpdate();

                        if (rowsUpdated > 0) {
                            System.out.println("Data updated successfully!");
                            updateDialog.dispose(); // Close the dialog after successful update
                            // Update the table model
                            int selectedRow = resultTable.getSelectedRow();
                            tableModel.setValueAt(updatedFirstname, selectedRow, 0);
                            tableModel.setValueAt(updatedLastname, selectedRow, 1);
                            tableModel.setValueAt(updatedStudentID, selectedRow, 2);
                            tableModel.setValueAt(updatedMajor, selectedRow, 3);
                            
                        }

                        ps.close();
                        conn.close();
                    } catch (SQLException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println("Please fill out all fields.");
                }
            }
        });

        updateDialog.add(new JLabel("Firstname:"));
        updateDialog.add(firstnameField);
        updateDialog.add(new JLabel("Lastname:"));
        updateDialog.add(lastnameField);
        updateDialog.add(new JLabel("Student ID:"));
        updateDialog.add(studentIDField);
        updateDialog.add(new JLabel("Major:"));
        updateDialog.add(majorField);
        updateDialog.add(updateButton);

        updateDialog.setSize(300, 200);
        updateDialog.setVisible(true);
    }
}
