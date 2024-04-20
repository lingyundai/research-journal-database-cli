/**
 * File: PublicationsAttrSearchView.java
 * Authors: Lauren Dennedy G01462079, Lingyun Dai G01464288
 * Class: CS 550 Database Systems
 * Assignment: Group Project - Project 2
 * Created: 4/18
 * Last Modified: 4/20
 */

package views;

import gui.Menu;
import sql.Queries;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;

public class PublicationsAttrSearchView
{

    public static void populateAttributePanel() 
    {
        JPanel attributePanel = new JPanel();
        attributePanel.setLayout(new BoxLayout(attributePanel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Input fields:");
        attributePanel.add(label);

        JSeparator separator1 = new JSeparator();
        attributePanel.add(separator1);

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

        JSeparator separator2 = new JSeparator();
        attributePanel.add(separator2);

        JButton backButton = new JButton("Back");
        JButton enterAttributeButton = new JButton("Enter");
        attributePanel.add(enterAttributeButton);
        attributePanel.add(backButton);

        enterAttributeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // no valid input prompt for inputs again
                if (authorTextField.getText().isEmpty() && titleTextField.getText().isEmpty() && 
                    yearTextField.getText().isEmpty() && typeTextField.getText().isEmpty()) 
                {
                    JLabel invalidInputLabel = new JLabel("Please enter valid input(s).");
                    attributePanel.add(invalidInputLabel);
                    attributePanel.revalidate();
                } else {
                    enterAttributeButtonActionPerformed(authorTextField.getText(), titleTextField.getText(), 
                    yearTextField.getText(), typeTextField.getText());
                }
            }
        });

        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu.backButtonActionPerformed();
            }
        });

        attributePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        attributePanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        Menu.getMenuFrame().add(attributePanel);
    }

    private static void enterAttributeButtonActionPerformed(String author, String title, String year, String type) 
    {
        Menu.clearFrame();
        populateOutputFieldPanel(author, title, year, type);
    }

    private static void populateOutputFieldPanel(String author, String title, String year, String type) 
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
        String[] options = {"p.PUBLICATIONID", "a.AUTHOR", "p.TITLE", "p.YEAR", "p.TYPE", "p.SUMMARY"};
        JComboBox<String> sortOptionComboBox = new JComboBox<String>(options);
        outputPanel.add(sortOptionComboBox);

        JButton enterOutputButton = new JButton("Enter");
        enterOutputButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enterOutputButtonActionPerformed(author, title, 
                    year, type, publicationIdOptionCheckbox.isSelected(),
                                titleOptionCheckbox.isSelected(),
                                yearOptionCheckbox.isSelected(),
                                typeOptionCheckbox.isSelected(),
                                summaryOptionCheckbox.isSelected(),
                                authorOptionCheckbox.isSelected(),
                                sortOptionComboBox.getSelectedItem().toString(), 
                                outputPanel);
            }
        });
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu.backButtonActionPerformed();
            }
        });
        outputPanel.add(enterOutputButton);
        outputPanel.add(backButton);
        outputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        outputPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        Menu.getMenuFrame().add(outputPanel);
    }

    private static void handleCheckboxSelection(String author, String title, 
                String year, String type, boolean displayPublicationId,
                boolean displayTitle,
                boolean displayYear,
                boolean displayType,
                boolean displaySummary,
                boolean displayAuthor,
                String sortOption, 
                JPanel outputPanel)
    {
        if (displayAuthor == false && displayTitle == false && displayPublicationId == false &&
            displaySummary == false && displayType == false && displayYear == false) {
                JLabel invalidInputLabel = new JLabel("Please check at least one option.");
                outputPanel.add(invalidInputLabel);
                outputPanel.revalidate();
        } else {
                populateResultingSearchPanel(author, title,
                year, type, displayPublicationId,
                displayTitle,
                displayYear, displayType,
                displaySummary, displayAuthor, 
                sortOption);
        }
    }

    private static void enterOutputButtonActionPerformed(String author, String title, 
                    String year, String type, boolean displayPublicationId,
                                boolean displayTitle,
                                boolean displayYear,
                                boolean displayType,
                                boolean displaySummary,
                                boolean displayAuthor,
                                String sortOption, JPanel outputPanel) {
        handleCheckboxSelection(author, title, year, type, displayPublicationId, displayTitle,
                    displayYear, displayType, displaySummary, displayAuthor, sortOption, outputPanel);
    }

    private static JTable filterOutputFieldsResult(String author, String title, 
                        String year, String type, boolean displayPublicationId,
                        boolean displayTitle,
                        boolean displayYear,
                        boolean displayType,
                        boolean displaySummary,
                        boolean displayAuthor,
                        String sortOption,
                        ArrayList<ArrayList<String>> publicationData)
    {
        int numChosen = 0;
        boolean[] chosenDisplays = {displayPublicationId, displayYear, displayType, displayTitle, displaySummary, displayAuthor};
        String[] publicationsColumns = {"Publication ID", "Year", "Type", "Title", "Summary", "Author"};
        String[] chosenColumns = {"", "", "", "", "", ""};
        for (int i = 0; i < publicationsColumns.length; i++) {
            if (chosenDisplays[i]) {
                chosenColumns[i] = publicationsColumns[i];
                numChosen++;
            }
        }

        String[][] publicationsData = {{""}};
        publicationsData = new String[publicationData.size()][numChosen];
        for (int i = 0; i < publicationData.size(); i++) {
            int chosenIndex = 0;
            for (int j = 0; j < publicationData.get(i).size(); j++) {
                if (!chosenColumns[j].isEmpty()) {
                    publicationsData[i][chosenIndex] = publicationData.get(i).get(j);
                    chosenIndex++;
                }
            }
        }

        int filteredChosen = 0;
        String[] filteredColumns = new String[numChosen];
        for (int i = 0; i < publicationsColumns.length; i++) {
            if (chosenDisplays[i]) {
                filteredColumns[filteredChosen] = publicationsColumns[i];
                filteredChosen++;
            }
        }

        JTable publicationsTable = new JTable(publicationsData, filteredColumns);
        return publicationsTable;
    }

    private static JPanel setUpFilteredOutputPanel()
    {
        Menu.clearFrame();
        JPanel resultingTablesPanel = new JPanel();
        resultingTablesPanel.setLayout(new BoxLayout(resultingTablesPanel, BoxLayout.Y_AXIS));

        return resultingTablesPanel;
    }

    private static void populateFilteredOutputPanel(JTable publicationsTable, 
                                            JPanel resultingTablesPanel)
    {
        publicationsTable.setFillsViewportHeight(true);
        JScrollPane publicationsPane = new JScrollPane(publicationsTable);
        resultingTablesPanel.add(publicationsPane);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu.backButtonActionPerformed();
            }
        });

        resultingTablesPanel.add(backButton);
        Menu.getMenuFrame().add(resultingTablesPanel);
    }

    private static void populateNoResultFoundPanel() 
    {
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("No results found, go back to main menu and try again.");
        outputPanel.add(label);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu.backButtonActionPerformed();
            }
        });
        outputPanel.add(backButton);
        outputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        outputPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        Menu.getMenuFrame().add(outputPanel);
    }

    private static void handleResultPanel(String author, String title, 
                        String year, String type, boolean displayPublicationId,
                        boolean displayTitle,
                        boolean displayYear,
                        boolean displayType,
                        boolean displaySummary,
                        boolean displayAuthor,
                        String sortOption,
                        ArrayList<ArrayList<String>> publicationData,
                        JPanel resultingTablesPanel) 
    {
        if (publicationData.size() == 0) {
            populateNoResultFoundPanel();
        } else {
            JTable publicationsTable = filterOutputFieldsResult(author, title, year, type, displayPublicationId,
            displayTitle, displayYear, displayType, displaySummary, displayAuthor, sortOption, publicationData);

            populateFilteredOutputPanel(publicationsTable, resultingTablesPanel);
        }
    }

    private static void populateResultingSearchPanel(String author, String title, 
                    String year, String type, boolean displayPublicationId,
                                boolean displayTitle,
                                boolean displayYear,
                                boolean displayType,
                                boolean displaySummary,
                                boolean displayAuthor,
                                String sortOption) {

        JPanel resultingTablesPanel = setUpFilteredOutputPanel();

        ArrayList<ArrayList<String>> publicationData = Queries.getAllDataFromCustomSearch(author, title, year, type, sortOption);

        handleResultPanel(author, title, year, type, displayPublicationId,
                        displayTitle, displayYear, displayType, displaySummary, displayAuthor, 
                        sortOption, publicationData, resultingTablesPanel);
    }
}
