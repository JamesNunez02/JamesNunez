
package com.mycompany.james;
        
import java.awt.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays; 


public class Sign_Up_Page extends JFrame implements ActionListener {
    private final JLabel lblSignUp, lblUsername, lblFullname, lblProfessor, lblNumber, lblPasswordfield, lblConfirmPassword;
    private final JTextField fldUsername, fldFullname, fldProfessor, fldNumber ;
    private final JButton btnClear, btnSubmit;
    private final JPasswordField pfPasswordfield, pfConfirmPassword;
    
    
    private static final String url = "jdbc:mysql://localhost:3306/database";
    private static final String user = "root";
    private static final String password  = "";
    
    Sign_Up_Page(){
        
        setTitle("SIGN UP AN ACCOUNT");
        setSize(700,500);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.LIGHT_GRAY);

    
        lblSignUp = new JLabel("SIGN UP AN ACCOUNT", SwingConstants.CENTER);
        lblSignUp.setFont(new Font("Helvetica", Font.BOLD, 30));
        lblSignUp.setBounds(85, 30, 500, 50);
        lblSignUp.setVisible(true);
        add(lblSignUp);
        
        lblUsername = new JLabel("USERNAME:");
        lblUsername.setBounds(50, 100, 75, 30);
        lblUsername.setFont(new Font("Helvetica", Font.BOLD, 12));
        add(lblUsername);
        
        lblFullname = new JLabel("FULL NAME:");
        lblFullname.setBounds(300,100, 75, 30);
        lblFullname.setFont(new Font("Helvetica", Font.BOLD, 12));
        lblFullname.setVisible(true);
        add(lblFullname);
        
        lblProfessor = new JLabel("PROFESSOR/INSTRUCTOR:");
        lblProfessor.setBounds(50, 200, 200, 30);
        lblProfessor.setFont(new Font("Helvetica", Font.BOLD, 12));
        add(lblProfessor);
        
        lblNumber = new JLabel("CELLPHONE NUMBER#");
        lblNumber.setBounds(50,250,200,30);
        lblNumber.setFont(new Font("Helvetica", Font.BOLD, 12));
        add(lblNumber);
       
        
        lblPasswordfield = new JLabel("CREATE PASSWORD:");
        lblPasswordfield.setBounds(50,300, 200, 30);
        lblPasswordfield.setFont(new Font("Helvetica", Font.BOLD, 12));
        add(lblPasswordfield);
        
        lblConfirmPassword = new JLabel("CONFIRM PASSWORD:");
        lblConfirmPassword.setBounds(50, 350, 200, 30);
        lblConfirmPassword.setFont(new Font("Helvetica", Font.BOLD, 12));
        add(lblConfirmPassword);
        
        fldFullname = new JTextField();
        fldFullname.setBounds(300, 130, 230, 30);
        fldFullname.setFont(new Font("Helvetica", Font.PLAIN, 12));
        add(fldFullname);
        
        fldUsername = new JTextField();
        fldUsername.setBounds(50, 130, 230, 30);
        fldUsername.setFont(new Font("Helvetica", Font.PLAIN, 12));
        add(fldUsername);
        
        fldProfessor = new JTextField();
        fldProfessor.setBounds(210, 200, 300, 30);
        fldProfessor.setFont(new Font("Helvetica", Font.PLAIN,12));
        add(fldProfessor);
        
        fldNumber = new JTextField();
        fldNumber.setBounds(210, 250, 300, 30);
        fldNumber.setFont(new Font("Helvetica", Font.PLAIN,12));
        add(fldNumber);
        
        pfPasswordfield = new JPasswordField();
        pfPasswordfield.setBounds(190,300,300,30);
        pfPasswordfield.setFont(new Font("Helvetica", Font.PLAIN,15));
        add(pfPasswordfield);
        
        pfConfirmPassword = new JPasswordField();
        pfConfirmPassword.setBounds(190,350,300,30);
        pfConfirmPassword.setFont(new Font("Helvetica", Font.PLAIN,15));
        add(pfConfirmPassword);
        
      
                
        btnClear = new JButton("CLEAR");
        btnClear.setBounds(230, 400, 100, 30);
        btnClear.setFont(new Font("Helvetica", Font.BOLD,15));
        add(btnClear);
        btnClear.addActionListener(this);
        
        btnSubmit = new JButton("SUBMIT");
        btnSubmit.setBounds(350, 400, 100, 30);
        btnSubmit.setFont(new Font("Helvetica", Font.BOLD,15));
        add(btnSubmit);
        btnSubmit.addActionListener(this);
      
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnClear) {
            clearFields();
        } else if (e.getSource() == btnSubmit) {
            submitdata();
            dispose();
        }
    }

    private void clearFields() {
        fldUsername.setText("");
        fldFullname.setText("");
        fldProfessor.setText("");
        fldNumber.setText("");
        pfPasswordfield.setText("");
        pfConfirmPassword.setText("");
    }

    private void submitdata() {
    String query = "INSERT INTO `data` (`Username`, `Full Name`, `Professor/Instructor`, `Contact Number`, `Password`) VALUES (?, ?, ?, ?, ?)";
    try (Connection connection = DriverManager.getConnection(url, user, password);
         PreparedStatement pstmt = connection.prepareStatement(query)) {
        
        String username = fldUsername.getText();
        String fullname = fldFullname.getText();
        String professor = fldProfessor.getText();
        String number = fldNumber.getText();
        char[] Password = pfPasswordfield.getPassword();

        if (!Arrays.equals(Password, pfConfirmPassword.getPassword())) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        pstmt.setString(1, username);
        pstmt.setString(2, fullname);
        pstmt.setString(3, professor);
        pstmt.setString(4, number);
        pstmt.setString(5, new String(Password)); 

        int rowsInserted = pstmt.executeUpdate();
        if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(this, "Created an Account Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose(); 
            new Login_Page().setVisible(true); 
        } else {
            JOptionPane.showMessageDialog(this, "Error creating account. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }
    public static void main(String[]args){
        Sign_Up_Page si = new Sign_Up_Page();
        si.setVisible(true);
    }
}       


