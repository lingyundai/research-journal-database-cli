package gui;
/**
 * File: Menu.java
 * Authors: Lauren Dennedy G01462079, Lin Dai _________
 * Class: CS 550 Database Systems
 * Assignment: Group Project - Project 2
 * Created: 4/17
 * Last Modified: 4/18
 */

import javax.swing.*;

import sql.Driver;
import views.PublicationsAttrSearchView;
import views.PublicationsIDSearchView;
import views.TableContentsView;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Menu {

    // The main GUI JFrame
    private static JFrame menuFrame;

    // Create a custom exit process so the driver can close if running
    // and exited using 'X' button.
    private static WindowListener exitListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            if (Driver.isConnected())
                Driver.closeConnection();
            System.exit(0);
        }
    };

    // The creation of the main GUI JFrame 
    public static void createAndShowGUI() {

        //Create and set up the window.
        menuFrame = new JFrame("Research Journal Database");
        menuFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        menuFrame.addWindowListener(Menu.exitListener);
        menuFrame.setPreferredSize(new Dimension(640, 480));

        // Use the populateSetup function to add components
        populateSetup();

        //Display the window.
        menuFrame.pack();
        menuFrame.setVisible(true);
    }

    public static JFrame getMenuFrame() { return menuFrame; }

    public static void clearFrame()
    {
        menuFrame.getContentPane().removeAll();
        menuFrame.pack();
        refreshFrame();
        menuFrame.setPreferredSize(new Dimension(640, 480));
    }

    private static void refreshFrame() { SwingUtilities.updateComponentTreeUI(menuFrame); }

    private static void populateSetup()
    {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Please enter credentials for signing in to Oracle SQL Server.");
        loginPanel.add(label);

        JLabel loginLabel = new javax.swing.JLabel("Username:");
        JTextField loginTextField = new javax.swing.JTextField();
        loginPanel.add(loginLabel);
        loginPanel.add(loginTextField);

        JLabel passwordLabel = new javax.swing.JLabel("Password");
        JTextField passwordTextField = new javax.swing.JTextField();
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordTextField);
        
        JButton enterButton = new javax.swing.JButton("Enter");
        enterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enterLoginButtonActionPerformed(evt, loginTextField.getText(), passwordTextField.getText());
            }
        });

        loginPanel.add(enterButton);
        menuFrame.add(loginPanel);
    }

    private static void enterLoginButtonActionPerformed(java.awt.event.ActionEvent evt, String username, String password)
    {
        Driver.connectToDB(username, password);
        if (Driver.isConnected()) {
            clearFrame();
            populateSQLFileMenu();
        } else {
            return;
        }
    }

    private static void populateSQLFileMenu()
    {
        JPanel sqlFilePanel = new JPanel();
        sqlFilePanel.setLayout(new BoxLayout(sqlFilePanel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Please enter the directory for the .sql file you would like to load.");
        sqlFilePanel.add(label);

        JLabel sqlFileLabel = new javax.swing.JLabel("SQL File:");
        JTextField sqlFileTextField = new javax.swing.JTextField();
        sqlFilePanel.add(sqlFileLabel);
        sqlFilePanel.add(sqlFileTextField);

        JButton enterButton = new javax.swing.JButton("Enter");
        enterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enterSQLFileButtonActionPerformed(evt, sqlFileTextField.getText());
            }
        });

        sqlFilePanel.add(enterButton);
        menuFrame.add(sqlFilePanel);
    }

    private static void enterSQLFileButtonActionPerformed(java.awt.event.ActionEvent evt, String sqlFile)
    {
        if (Driver.loadSqlFromFile(sqlFile))
        {
            clearFrame();
            populateMainMenu();
        }
    }

    private static void populateMainMenu()
    {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("Main Menu");
        menuPanel.add(label);

        JButton tableContentsButton = new javax.swing.JButton("View Table Contents");
        tableContentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableContentsButtonActionPerformed(evt);
            }
        });

        menuPanel.add(tableContentsButton);

        JButton publicationIdButton = new javax.swing.JButton("Search by PUBLICATIONID");
        publicationIdButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                publicationIdButtonActionPerformed(evt);
            }
        });

        menuPanel.add(publicationIdButton);

        JButton attributeButton = new javax.swing.JButton("Search by one or more attributes");
        attributeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attributeButtonActionPerformed(evt);
            }
        });

        menuPanel.add(attributeButton);

        JButton exitButton = new javax.swing.JButton("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Driver.closeConnection();
                System.exit(0);
            }
        });

        menuPanel.add(exitButton);
        menuFrame.add(menuPanel);
    }

    public static void backButtonActionPerformed() {
        clearFrame();
        populateMainMenu();
    }

    private static void tableContentsButtonActionPerformed(java.awt.event.ActionEvent evt) {
        clearFrame();
        TableContentsView.populateTableOptionsPanel();
    }

    private static void publicationIdButtonActionPerformed(java.awt.event.ActionEvent evt) {
        clearFrame();
        PublicationsIDSearchView.populatePublicationIdPanel();
    }
    
    private static void attributeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        clearFrame();
        PublicationsAttrSearchView.populateAttributePanel();
    }
}