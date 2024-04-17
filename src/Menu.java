/*
Your command-line interface should include the following functionalities.
1. The user can view the contents of each table. Show the name of each table and display the
tuples of the table(s) that the user selects. (The user can select more than one tables to view.)

2. The user can simply search by PUBLICATIONID and return all attributes from
PUBLICATIONS table and include a number of authors field in the search result.

3. The user can search the database by specifying one or more input attributes from {AUTHOR,
TITLE, YEAR, TYPE} and specify one or more output attributes from both tables. In addition,
the user can specify a field on which the search result should be sorted. Your search should
consider pattern matching for input strings.

4. Your program should exit only when the user chooses to.
*/
import javax.swing.*;

public class Menu {

    private static JFrame menuFrame;

    public static void createAndShowGUI() {
        //Create and set up the window.
        menuFrame = new JFrame("HelloWorldSwing");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
    }

    private static void refreshFrame()
    {
        SwingUtilities.updateComponentTreeUI(menuFrame);
    }

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
        Driver.loadSqlFromFile(sqlFile);
    }

   /*
    private static void populateMenu()
    {

    }
    */
}