/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.james;


import javax.swing.*;
import java.awt.*;

public class Loading extends JFrame {
    private JProgressBar progressBar;
    private JLabel lblName;

    public Loading() {
        setTitle("Loading...");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.LIGHT_GRAY);

        lblName = new JLabel("Processing...", JLabel.CENTER);
        lblName.setFont(new Font("Arial", Font.BOLD, 20));

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true); 

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(lblName, BorderLayout.NORTH);
        panel.add(progressBar, BorderLayout.CENTER);

        add(panel);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    final int progress = i;
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            progressBar.setValue(progress);
                            if (progress == 100) {
                                lblName.setText("Loading completed. Redirecting...");
                                try {
                                    Thread.sleep(2000); 
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                redirectToOtherScreen();
                            }
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    private void redirectToOtherScreen() {
 
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Data().setVisible(true); 
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Loading().setVisible(true);
            }
        });
    }
}
