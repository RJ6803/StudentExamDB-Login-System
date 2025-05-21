
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.*;

public class StudentExamDB extends JFrame {
    // JDBC URL, username and password for your MySQL database.
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/student_db";
    static final String USER = "root";
    static final String PASS = "Rohit@6803";

    // GUI components
    private JTextField tfReg, tfName, tfMark1, tfMark2, tfMark3, tfMark4, tfMark5, tfMark6;
    private JButton btnInsert, btnDelete, btnUpdate,btnClear;
    
    // Database connection
    private Connection conn;

    public StudentExamDB() {
        super("Student Examination Database System");
        initComponents();
        createDBConnection();
    }

    private void initComponents() {
        // Using a GridLayout for simplicity
        setLayout(new GridLayout(10, 2, 10, 10));

        // Registration Number and Name fields
        add(new JLabel("Registration Number:"));
        tfReg = new JTextField();
        add(tfReg);

        add(new JLabel("Name:"));
        tfName = new JTextField();
        add(tfName);

        // Fields for marks in 6 subjects
        add(new JLabel("Mark 1:"));
        tfMark1 = new JTextField();
        add(tfMark1);

        add(new JLabel("Mark 2:"));
        tfMark2 = new JTextField();
        add(tfMark2);

        add(new JLabel("Mark 3:"));
        tfMark3 = new JTextField();
        add(tfMark3);

        add(new JLabel("Mark 4:"));
        tfMark4 = new JTextField();
        add(tfMark4);

        add(new JLabel("Mark 5:"));
        tfMark5 = new JTextField();
        add(tfMark5);

        add(new JLabel("Mark 6:"));
        tfMark6 = new JTextField();
        add(tfMark6);

        

        // Buttons for operations
        btnInsert = new JButton("Insert");
        btnDelete = new JButton("Delete");
        btnUpdate = new JButton("Update");
        btnClear = new JButton("Clear");

        // Adding buttons to the frame
        add(btnInsert);
        add(btnDelete);
        add(btnUpdate);
        add(btnClear);

        // Button action listeners
        btnInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertRecord();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteRecord();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateRecord();
            }
        });

        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        // Final frame settings
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
    }

    private void createDBConnection() {
        try {
            // Load JDBC driver (MySQL Connector/J)
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to the database successfully!");
        } catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection failed: " + ex.getMessage());
        }
    }

    private void logOperation(String operation, String details) {
        try (FileWriter fw = new FileWriter("student_log.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(operation + " operation: " + details + " at " + new java.util.Date());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void insertRecord() {
        String sql = "INSERT INTO student (reg_no, name, marks1, marks2, marks3, marks4, marks5, marks6) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, tfReg.getText());
            pst.setString(2, tfName.getText());
            pst.setInt(3, Integer.parseInt(tfMark1.getText()));
            pst.setInt(4, Integer.parseInt(tfMark2.getText()));
            pst.setInt(5, Integer.parseInt(tfMark3.getText()));
            pst.setInt(6, Integer.parseInt(tfMark4.getText()));
            pst.setInt(7, Integer.parseInt(tfMark5.getText()));
            pst.setInt(8, Integer.parseInt(tfMark6.getText()));
            int rows = pst.executeUpdate();
            JOptionPane.showMessageDialog(this, rows + " record(s) inserted.");
            String details = "Reg No: " + tfReg.getText() + ", Name: " + tfName.getText() + ", marks1: "+ tfMark1.getText() + ", marks2: " + tfMark2.getText() + ", marks3: " + tfMark3.getText() + ", marks4: " + tfMark4.getText() + ", marks5: " + tfMark5.getText() + ", marks6: " + tfMark6.getText();
            logOperation("INSERT", details);
        } catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error inserting record: " + ex.getMessage());
        }
    }

    private void deleteRecord() {
        String sql = "DELETE FROM student WHERE reg_no = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, tfReg.getText());
            int rows = pst.executeUpdate();
            JOptionPane.showMessageDialog(this, rows + " record(s) deleted.");
            String details = "Reg No: " + tfReg.getText();
            logOperation("DELETE", details);
        } catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting record: " + ex.getMessage());
        }
    }

    private void updateRecord() {
        String sql = "UPDATE student SET name = ?, marks1 = ?, marks2 = ?, marks3 = ?, marks4 = ?, marks5 = ?, marks6 = ? WHERE reg_no = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, tfName.getText());
            pst.setInt(2, Integer.parseInt(tfMark1.getText()));
            pst.setInt(3, Integer.parseInt(tfMark2.getText()));
            pst.setInt(4, Integer.parseInt(tfMark3.getText()));
            pst.setInt(5, Integer.parseInt(tfMark4.getText()));
            pst.setInt(6, Integer.parseInt(tfMark5.getText()));
            pst.setInt(7, Integer.parseInt(tfMark6.getText()));
            pst.setString(8, tfReg.getText());
            int rows = pst.executeUpdate();
            JOptionPane.showMessageDialog(this, rows + " record(s) updated.");
            String details = "Reg No: " + tfReg.getText() + ", Name: " + tfName.getText()  + ", marks1: "+ tfMark1.getText() + ", marks2: " + tfMark2.getText() + ", marks3: " + tfMark3.getText() + ", marks4: " + tfMark4.getText() + ", marks5: " + tfMark5.getText() + ", marks6: " + tfMark6.getText();
            logOperation("UPDATE", details);
        } catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating record: " + ex.getMessage());
        }
    }
    private void clearFields() {
        tfReg.setText("");
        tfName.setText("");
        tfMark1.setText("");
        tfMark2.setText("");
        tfMark3.setText("");
        tfMark4.setText("");
        tfMark5.setText("");
        tfMark6.setText("");
    }

    public static void main(String[] args) {
        // Start the Swing application on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StudentExamDB();
            }
        });
    }
}


