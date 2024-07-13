/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.james;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Login_Page extends JFrame implements ActionListener {
    private final JLabel lblLogin, lblUsername, lblPassword;
    private final JTextField txtUsername;
    private final JPasswordField jPPassword;
    private final JButton btnSubmit;
    private final JButton btnBack;

    private static final String URL = "jdbc:mysql://localhost:3306/database";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public Login_Page() {
        setTitle("Log In");
        setSize(500,500);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.LIGHT_GRAY);


        JPanel panel = new JPanel();
        getContentPane().add(panel);
        
        lblLogin = new JLabel("LOG IN", SwingConstants.CENTER);
        lblLogin.setFont(new Font("Helvetica", Font.BOLD, 30));
        lblLogin.setBounds(6, 75, 500, 50);
        lblLogin.setVisible(true);
        add(lblLogin);

        lblUsername = new JLabel("USERNAME:");
        lblUsername.setBounds(45, 200, 100, 30);
        lblUsername.setFont(new Font("Helvetica", Font.BOLD, 12));
        add(lblUsername);
        
        txtUsername = new JTextField();
        txtUsername.setBounds(120, 200, 300, 30);
        txtUsername.setFont(new Font("Helvetica", Font.PLAIN, 12));
        add(txtUsername);
        
        lblPassword = new JLabel("PASSWORD:");
        lblPassword.setBounds(45,250, 100, 30);
        lblPassword.setFont(new Font("Helvetica", Font.BOLD, 12));
        lblPassword.setVisible(true);
        add(lblPassword);
        
        jPPassword = new JPasswordField();
        jPPassword.setBounds(120, 250, 300, 30);
        jPPassword.setFont(new Font("Helvetica", Font.PLAIN, 12));
        add(jPPassword);
        
        btnSubmit = new JButton("SUBMIT");
        btnSubmit.setBounds(150, 300, 100, 30);
        btnSubmit.setFont(new Font("Helvetica", Font.BOLD,15));
        add(btnSubmit);
        btnSubmit.addActionListener(this);
        
        btnBack = new JButton("BACK");
        btnBack.setBounds(280, 300, 100, 30);
        btnBack.setFont(new Font("Helvetica", Font.BOLD,15));
        add(btnBack);
        btnBack.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    if (e.getSource() == btnSubmit) {
        String username = txtUsername.getText().trim();
        String password = new String(jPPassword.getPassword()).trim();

        try {
            if (validateUser(username, password)) {
                JOptionPane.showMessageDialog(this, "Login successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new Loading().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); 
            JOptionPane.showMessageDialog(this, "Login failed", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else if (e.getSource() == btnBack) {
        dispose();
        Sign_Up_Page si = new Sign_Up_Page();
        si.setVisible(true);
    }
}

    private boolean validateUser(String username, String password) throws SQLException {
    String query = "SELECT * FROM `data` WHERE `Username` = ? AND `Password` = ?";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    }
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login_Page loginPage = new Login_Page();
            loginPage.setVisible(true);
        });
    }
}

