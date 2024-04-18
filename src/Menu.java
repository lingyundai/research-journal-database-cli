import javax.swing.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

public class Menu {

    private static JFrame menuFrame;

    public static void createAndShowGUI() {
        //Create and set up the window.
        menuFrame = new JFrame("Research Journal Database");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setPreferredSize(new Dimension(640, 480));

        // Use the populateSetup function to add components
        populateSetup();

        //Display the window.
        menuFrame.pack();
        menuFrame.setVisible(true);
    }

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

    private static void clearFrame()
    {
        menuFrame.getContentPane().removeAll();
        menuFrame.pack();
        refreshFrame();
        menuFrame.setPreferredSize(new Dimension(640, 480));
    }

    private static void refreshFrame() { SwingUtilities.updateComponentTreeUI(menuFrame); }

    private static void populateSQLFileMenu()
    {
        JPanel sqlFilePanel = new JPanel();
        sqlFilePanel.setLayout(new BoxLayout(sqlFilePanel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Please enter directory for the .sql file you would like to load.");
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

    private static void tableContentsButtonActionPerformed(java.awt.event.ActionEvent evt) {
        clearFrame();
        populateTableOptionsPanel();
    }

    private static void publicationIdButtonActionPerformed(java.awt.event.ActionEvent evt) {
        clearFrame();
        populatePublicationIdPanel();
    }
    
    private static void attributeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        clearFrame();
        populateAttributePanel();
    }

    private static void populateTableOptionsPanel()
    {
        JPanel contentsOptionPanel = new JPanel();

        JLabel label = new JLabel("Select Tables to View:");
        contentsOptionPanel.add(label);

        JLabel publicationsOptionLabel = new JLabel("PUBLICATIONS");
        contentsOptionPanel.add(publicationsOptionLabel);
        
        JCheckBox publicationsOptionCheckBox = new JCheckBox();
        contentsOptionPanel.add(publicationsOptionCheckBox);

        JLabel authorsOptionLabel = new JLabel("AUTHORS");
        contentsOptionPanel.add(authorsOptionLabel);
        
        JCheckBox authorsOptionCheckBox = new JCheckBox();
        contentsOptionPanel.add(authorsOptionCheckBox);
        
        JButton enterTableOptionsButton = new JButton("Enter");
        enterTableOptionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                return;
            }
        });
        contentsOptionPanel.add(enterTableOptionsButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed();
            }
        });
        contentsOptionPanel.add(backButton);
        contentsOptionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentsOptionPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        menuFrame.add(contentsOptionPanel);
    }

    private static void populatePublicationIdPanel()
    {
        JPanel publicationOptionPanel = new JPanel();
        publicationOptionPanel.setLayout(new BoxLayout(publicationOptionPanel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("Enter a Publication ID to search for:");
        publicationOptionPanel.add(label);

        JTextField publicationIDField = new javax.swing.JTextField();
        publicationOptionPanel.add(publicationIDField);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed();
            }
        });
        publicationOptionPanel.add(backButton);
        publicationOptionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        publicationOptionPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        menuFrame.add(publicationOptionPanel);
    }

        private static void populateAttributePanel() 
    {
        JPanel attributePanel = new JPanel();
        attributePanel.setLayout(new BoxLayout(attributePanel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Input fields:");
        attributePanel.add(label);

        JLabel authorInputLabel = new javax.swing.JLabel("AUTHOR:");
        JTextField authorTextField = new javax.swing.JTextField();
        attributePanel.add(authorInputLabel);
        attributePanel.add(authorTextField);

        JLabel titleInputLabel = new javax.swing.JLabel("TITLE:");
        JTextField titleTextField = new javax.swing.JTextField();
        attributePanel.add(titleInputLabel);
        attributePanel.add(titleTextField);

        JLabel yearInputLabel = new javax.swing.JLabel("YEAR:");
        JTextField yearTextField = new javax.swing.JTextField();
        attributePanel.add(yearInputLabel);
        attributePanel.add(yearTextField);

        JLabel typeInputLabel = new javax.swing.JLabel("TYPE:");
        JTextField typeTextField = new javax.swing.JTextField();
        attributePanel.add(typeInputLabel);
        attributePanel.add(typeTextField);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed();
            }
        });
        attributePanel.add(backButton);
        attributePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        attributePanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        menuFrame.add(attributePanel);
    }

    private static void populateOutputFieldPanel() 
    {
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Output fields:");
        outputPanel.add(label);

        JCheckBox publicationIdOptionCheckbox = new JCheckBox("PUBLICATIONID");
        outputPanel.add(publicationIdOptionCheckbox);

        JCheckBox authorOptionCheckbox = new JCheckBox("AUTHOR");
        outputPanel.add(authorOptionCheckbox);

        JCheckBox titleOptionCheckbox = new JCheckBox("TITLE");
        outputPanel.add(titleOptionCheckbox);

        JCheckBox yearOptionCheckbox = new JCheckBox("YEAR");
        outputPanel.add(yearOptionCheckbox);

        JCheckBox typeOptionCheckbox = new JCheckBox("TYPE");
        outputPanel.add(typeOptionCheckbox);

        JCheckBox summaryOptionCheckbox = new JCheckBox("SUMMARY");
        outputPanel.add(summaryOptionCheckbox);

        JLabel sortedBylabel = new JLabel("Sorted by:");
        outputPanel.add(sortedBylabel);
        JCheckBox ascOptionCheckbox = new JCheckBox("ASC");
        outputPanel.add(ascOptionCheckbox);
        JCheckBox descOptionCheckbox = new JCheckBox("DESC");
        outputPanel.add(descOptionCheckbox);
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed();
            }
        });
        outputPanel.add(backButton);
        outputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        outputPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        menuFrame.add(outputPanel);
    }

    private static void backButtonActionPerformed() {
        clearFrame();
        populateMainMenu();
    }
}