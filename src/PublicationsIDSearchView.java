/**
 * File: PublicationsIDSearchView.java
 * Authors: Lauren Dennedy G01462079, Lin Dai _________
 * Class: CS 550 Database Systems
 * Assignment: Group Project - Project 2
 * Created: 4/18
 * Last Modified: 4/18
 */

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

class PublicationsIDSearchView
{
    public static void populatePublicationIdPanel()
    {
        JPanel publicationOptionPanel = new JPanel();
        publicationOptionPanel.setLayout(new BoxLayout(publicationOptionPanel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("Enter a Publication ID to search for:");
        publicationOptionPanel.add(label);

        JTextField publicationIDField = new javax.swing.JTextField();
        publicationOptionPanel.add(publicationIDField);

        JButton backButton = new JButton("Back");
        JButton enterPublicationIdButton = new JButton("Enter");
        enterPublicationIdButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (publicationIDField.getText() != null) {
                    enterPublicationIdActionPerformed(publicationIDField.getText());
                } else {
                    System.out.println("\nUser must select either publication ID to view.");
                }
            }
        });
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu.backButtonActionPerformed();
            }
        });
        publicationOptionPanel.add(backButton);
        publicationOptionPanel.add(enterPublicationIdButton);
        publicationOptionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        publicationOptionPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        Menu.getMenuFrame().add(publicationOptionPanel);
    }

    private static void enterPublicationIdActionPerformed(String publicationId) 
    {
        populateResultingTuplePanel(publicationId);
    }

    private static void populateResultingTuplePanel(String publicationId) 
    {
        String[][] publicationsIdData = {{""}};
        ArrayList<ArrayList<String>> publicationIdData = Driver.getPublicationIdTupleData(publicationId);
        System.out.println("here " + publicationIdData);
        publicationsIdData = new String[publicationIdData.size()][publicationIdData.get(0).size()];
        System.out.println("next " + publicationsIdData);
        for (int i = 0; i < publicationIdData.size(); i++) {
            for (int j = 0; j < publicationIdData.get(i).size(); j++) {
                publicationsIdData[i][j] = publicationIdData.get(i).get(j);
            }
        }

        Menu.clearFrame();
        JPanel resultingTablesPanel = new JPanel();
        resultingTablesPanel.setLayout(new BoxLayout(resultingTablesPanel, BoxLayout.Y_AXIS));

        String[] publicationsColumns = {"Publication ID", "Year", "Type", "Title", "Summary"};
        JTable publicationIdTable = new JTable(publicationsIdData, publicationsColumns);
        publicationIdTable.setFillsViewportHeight(true);
        JScrollPane publicationIdPane = new JScrollPane(publicationIdTable);
        resultingTablesPanel.add(publicationIdPane);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu.backButtonActionPerformed();
            }
        });
        resultingTablesPanel.add(backButton);
        Menu.getMenuFrame().add(resultingTablesPanel);
        return;
    }
}