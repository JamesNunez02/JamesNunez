/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.james;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Data {

    private JFrame frame;
    private JTextField usernamefld, fullnamefld, proffld, contactfld,passwordfld ;
    private JPasswordField passwordField;
    private JTable dataTable;

    private final String url = "jdbc:mysql://localhost:3306/database";
    private final String user = "root";
    private final String dbPassword = ""; 

    private DefaultTableModel tableModel;

    public Data() {
        initialize();
        populateTable();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Data");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        String[] columns = {"Username", "Full Name", "Professor/Instructor", "Contact Number"};
        tableModel = new DefaultTableModel(columns, 0);
        dataTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setBounds(50, 20, 700, 300);
        frame.getContentPane().add(scrollPane);

        JLabel username = new JLabel("USERNAME:");
        username.setBounds(50, 340, 120, 20);
        frame.getContentPane().add(username);

        usernamefld = new JTextField();
        usernamefld.setBounds(180, 340, 160, 20);
        frame.getContentPane().add(usernamefld);
        usernamefld.setColumns(10);

        JLabel fullname = new JLabel("FULL NAME:");
        fullname.setBounds(50, 370, 120, 20);
        frame.getContentPane().add(fullname);

        fullnamefld = new JTextField();
        fullnamefld.setBounds(180, 370, 160, 20);
        frame.getContentPane().add(fullnamefld);
        fullnamefld.setColumns(10);

        JLabel Professor = new JLabel("PROFESSOR/INSTRUCTOR:");
        Professor.setBounds(50, 400, 120, 20);
        frame.getContentPane().add(Professor);

        proffld = new JTextField();
        proffld.setBounds(180, 400, 160, 20);
        frame.getContentPane().add(proffld);
        proffld.setColumns(10);

        JLabel contactlbl = new JLabel("CONTACT NUMBER:");
        contactlbl.setBounds(50, 430, 120, 20);
        frame.getContentPane().add(contactlbl);

        contactfld = new JTextField();
        contactfld.setBounds(180, 430, 160, 20);
        frame.getContentPane().add(contactfld);
        contactfld.setColumns(10);

        JLabel passwordLabel = new JLabel("PASSWORD:");
        passwordLabel.setBounds(50, 460, 120, 20);
        frame.getContentPane().add(passwordLabel);

        passwordField = new JPasswordField(); 
        passwordField.setBounds(180, 460, 160, 20);
        frame.getContentPane().add(passwordField);

        JButton registerButton = new JButton("REGISTER");
        registerButton.setBounds(50, 490, 100, 30);
        frame.getContentPane().add(registerButton);

        JButton deleteButton = new JButton("DELETE");
        deleteButton.setBounds(170, 490, 100, 30);
        frame.getContentPane().add(deleteButton);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username =  usernamefld.getText();
                String fullname = fullnamefld.getText();
                String prof = proffld.getText();
                String contactnumber = contactfld.getText();
                char[] inputPassword = passwordField.getPassword(); 
                registerStudent(username, fullname, prof, contactnumber, inputPassword);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });
    }

    private void populateTable() {
        String sql = "SELECT * FROM `data`";

        try (
                Connection connection = DriverManager.getConnection(url, user, dbPassword);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            tableModel.setRowCount(0);

            while (resultSet.next()) {
                String username = resultSet.getString("Username");
                String fullname = resultSet.getString("Full Name");
                String prof = resultSet.getString("Professor/Instructor");
                String contactnumber = resultSet.getString("Contact Number");

                Object[] row = {username, fullname, prof, contactnumber};
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error populating table:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void registerStudent(String username, String fullname, String professor, String contactnumber, char[] inputPassword) {
        String sql = "INSERT INTO `data` (`Username`, `Full Name`, `Professor/Instructor`, `Contact Number`, `Password`) VALUES (?, ?, ?, ?, ?)";

        try (
                Connection connection = DriverManager.getConnection(url, user, dbPassword);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, fullname);
            preparedStatement.setString(3, professor);
            preparedStatement.setString(4, contactnumber);
            preparedStatement.setString(5, new String(inputPassword));

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(frame, "A new account has been registered successfully.");
                populateTable();
                clearFields();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error registering the student:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void deleteStudent() {
        int selectedRowIndex = dataTable.getSelectedRow();

        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String username = (String) tableModel.getValueAt(selectedRowIndex, 3); 
        String sql = "DELETE FROM `data` WHERE `Username` = ?";

        try (
                Connection connection = DriverManager.getConnection(url, user, dbPassword);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, username);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(frame, "Record deleted successfully.");
                tableModel.removeRow(selectedRowIndex); 
                clearFields();
            } else {
                JOptionPane.showMessageDialog(frame, "No record found for deletion.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error deleting record:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void clearFields() {
        usernamefld.setText("");
        fullnamefld.setText("");
        proffld.setText("");
        contactfld.setText("");
        passwordField.setText(""); 
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Data data = new Data();
                    data.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

