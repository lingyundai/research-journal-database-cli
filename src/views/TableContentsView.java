/**
 * File: TableContentsView.java
 * Authors: Lauren Dennedy G01462079, Lingyun Dai G01464288
 * Class: CS 550 Database Systems
 * Assignment: Group Project - Project 2
 * Created: 4/18
 * Last Modified: 4/18
 */

package views;

import gui.Menu;
import sql.Queries;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;

public class TableContentsView
{
    public static void populateTableOptionsPanel()
    {
        JPanel contentsOptionPanel = new JPanel();
        contentsOptionPanel.setLayout(new BoxLayout(contentsOptionPanel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Select Tables to View:");
        contentsOptionPanel.add(label);

        JSeparator separator1 = new JSeparator();
        separator1.setAlignmentY(Component.TOP_ALIGNMENT);
        contentsOptionPanel.add(separator1);

        JPanel contentsOptionSubPanel = new JPanel();
        contentsOptionSubPanel.setLayout(new GridLayout(2, 1, 10, 20));
        contentsOptionSubPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JCheckBox publicationsOptionCheckBox = new JCheckBox("PUBLICATIONS");
        contentsOptionSubPanel.add(publicationsOptionCheckBox);
        
        JCheckBox authorsOptionCheckBox = new JCheckBox("AUTHORS");
        contentsOptionSubPanel.add(authorsOptionCheckBox);

        contentsOptionPanel.add(contentsOptionSubPanel);

        JSeparator separator2 = new JSeparator();
        separator2.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        contentsOptionPanel.add(separator2);
        
        JButton enterTableOptionsButton = new JButton("Enter");
        enterTableOptionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (publicationsOptionCheckBox.isSelected() || authorsOptionCheckBox.isSelected()) {
                    enterTableOptionsActionPerformed(publicationsOptionCheckBox.isSelected(), authorsOptionCheckBox.isSelected());
                } else {
                    System.out.println("\nUser must select either publications or authors to view.");
                }
            }
        });
        contentsOptionPanel.add(enterTableOptionsButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu.backButtonActionPerformed();
            }
        });
        contentsOptionPanel.add(backButton);
        contentsOptionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentsOptionPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        Menu.getMenuFrame().add(contentsOptionPanel);
    }

    private static void enterTableOptionsActionPerformed(boolean viewPublications, boolean viewAuthors) {
        populateResultingTablesPanel(viewPublications, viewAuthors);
    }

    private static void populateResultingTablesPanel(boolean viewPublications, boolean viewAuthors)
    {
        String[][] publicationData = {{""}};
        if (viewPublications) {
            ArrayList<ArrayList<String>> publicationsData = Queries.getPublicationsData();
            publicationData = new String[publicationsData.size()][publicationsData.get(0).size()];
            for (int i = 0; i < publicationsData.size(); i++) {
                for (int j = 0; j < publicationsData.get(i).size(); j++) {
                    publicationData[i][j] = publicationsData.get(i).get(j);
                }
            }
        }

        String[][] authorData = {{""}};
        if (viewAuthors) {
        ArrayList<ArrayList<String>> authorsData = Queries.getAuthorsData();
            authorData = new String[authorsData.size()][authorsData.get(0).size()];
            for (int i = 0; i < authorsData.size(); i++) {
                for (int j = 0; j < authorsData.get(i).size(); j++) {
                    authorData[i][j] = authorsData.get(i).get(j);
                }
            }
        }

        Menu.clearFrame();
        JPanel resultingTablesPanel = new JPanel();
        resultingTablesPanel.setLayout(new BoxLayout(resultingTablesPanel, BoxLayout.Y_AXIS));

        if (viewPublications && viewAuthors)
        {
            JSplitPane bothTables = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

            String[] publicationsColumns = {"Publication ID", "Year", "Type", "Title", "Summary"};
            JTable publicationsTable = new JTable(publicationData, publicationsColumns);
            publicationsTable.setFillsViewportHeight(true);
            JScrollPane publicationsPane = new JScrollPane(publicationsTable);

            String[] authorsColumns = {"Publication ID", "Name"};
            JTable authorsTable = new JTable(authorData, authorsColumns);
            authorsTable.setFillsViewportHeight(true);
            JScrollPane authorsPane = new JScrollPane(authorsTable);

            bothTables.add(publicationsPane);
            bothTables.add(authorsPane);
            bothTables.setDividerLocation(1.0);
            resultingTablesPanel.add(bothTables);
        }
        else
        {
            // One or the other
            if (viewPublications) {
                String[] publicationsColumns = {"Publication ID", "Year", "Type", "Title", "Summary"};
                JTable publicationsTable = new JTable(publicationData, publicationsColumns);
                publicationsTable.setFillsViewportHeight(true);
                JScrollPane publicationsPane = new JScrollPane(publicationsTable);
                resultingTablesPanel.add(publicationsPane);

            } else if (viewAuthors) {
                String[] authorsColumns = {"Publication ID", "Name"};
                JTable authorsTable = new JTable(authorData, authorsColumns);
                authorsTable.setFillsViewportHeight(true);
                JScrollPane authorsPane = new JScrollPane(authorsTable);
                resultingTablesPanel.add(authorsPane);
            }
        }

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
