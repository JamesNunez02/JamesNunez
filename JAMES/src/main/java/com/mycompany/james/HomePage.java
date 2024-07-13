/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.james;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class HomePage extends JFrame implements ActionListener {
    private final JLabel lblSignInPage;
    private final JButton btnSignUp, btnLogin;
    
    HomePage(){
        setTitle("SIGN IN PAGE");
        setSize(700,600);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setVisible(true);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        lblSignInPage = new JLabel("SIGN IN PAGE",SwingConstants.CENTER);
        lblSignInPage.setBounds(190,100,300,40);
        lblSignInPage.setFont(new Font("Arial",Font.BOLD,30));
        add(lblSignInPage);
        
        btnSignUp = new JButton("SIGN UP");
        btnSignUp.setBounds(190,250,300,40);
        btnSignUp.setFont(new Font("Roboto",Font.BOLD,20));
        btnSignUp.addActionListener(this);
        add(btnSignUp);
        
        btnLogin = new JButton("LOG IN");
        btnLogin.setBounds(190, 300, 300, 40);
        btnLogin.setFont(new Font("Roboto",Font.BOLD,20));
        btnLogin.addActionListener(this);
        add(btnLogin);
         
        
        
    }    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSignUp) {
            new Sign_Up_Page().setVisible(true);
            dispose();            
        }else if (e.getSource() == btnLogin){
            new Login_Page().setVisible(true);
    }
    }
    public static void main(String[]args){
        HomePage ho = new HomePage();
        ho.setVisible(true);
    }


    }
    

