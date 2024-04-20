/**
 * File: Menu.java
 * Authors: Lauren Dennedy G01462079, Lingyun Dai G01464288
 * Class: CS 550 Database Systems
 * Assignment: Group Project - Project 2
 * Created: 4/17
 * Last Modified: 4/18
 */

package gui;

import javax.swing.*;

import sql.Driver;
import views.PublicationsAttrSearchView;
import views.PublicationsIDSearchView;
import views.TableContentsView;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
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

        JSeparator separator1 = new JSeparator();
        loginPanel.add(separator1);

        JLabel loginLabel = new javax.swing.JLabel("Username:");
        JTextField loginTextField = new javax.swing.JTextField();
        loginPanel.add(loginLabel);
        loginPanel.add(loginTextField);

        JLabel passwordLabel = new javax.swing.JLabel("Password");
        JPasswordField passwordTextField = new javax.swing.JPasswordField();
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordTextField);
        
        JButton enterButton = new javax.swing.JButton("Enter");
        enterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                char[] passwordArray = passwordTextField.getPassword();
                String password = "";
                for (char c : passwordArray) {
                    password = password + c;
                }
                passwordArray = null;
                enterLoginButtonActionPerformed(evt, loginTextField.getText(), password);
                password = null;
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

        JSeparator separator1 = new JSeparator();
        sqlFilePanel.add(separator1);

        JLabel label2 = new JLabel("If no valid filepath is entered, the default shall be loaded.");
        sqlFilePanel.add(label2);

        JLabel label3 = new JLabel("(Default filepath is \"res/paper.sql\")");
        sqlFilePanel.add(label3);

        JSeparator separator2 = new JSeparator();
        sqlFilePanel.add(separator2);

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

        JSeparator separator1 = new JSeparator();
        separator1.setAlignmentY(Component.TOP_ALIGNMENT);
        menuPanel.add(separator1);

        JPanel menuSubOptionsPanel = new JPanel();
        menuSubOptionsPanel.setLayout(new GridLayout(3, 1, 20, 20));
        menuSubOptionsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JButton tableContentsButton = new javax.swing.JButton("View Table Contents");
        tableContentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableContentsButtonActionPerformed(evt);
            }
        });

        menuSubOptionsPanel.add(tableContentsButton);

        JButton publicationIdButton = new javax.swing.JButton("Search by PUBLICATIONID");
        publicationIdButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                publicationIdButtonActionPerformed(evt);
            }
        });

        menuSubOptionsPanel.add(publicationIdButton);

        JButton attributeButton = new javax.swing.JButton("Search by one or more attributes");
        attributeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attributeButtonActionPerformed(evt);
            }
        });

        menuSubOptionsPanel.add(attributeButton);
        menuPanel.add(menuSubOptionsPanel);

        JPanel menuSpacer = new JPanel();
        menuSpacer.setLayout(new BorderLayout());
        menuSpacer.add(new JPanel(), BorderLayout.CENTER);
        menuPanel.add(menuSpacer);

        JSeparator separator2 = new JSeparator();
        separator2.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        menuPanel.add(separator2);

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