import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * File: PublicationsIDSearchView.java
 * Authors: Lauren Dennedy G01462079, Lin Dai _________
 * Class: CS 550 Database Systems
 * Assignment: Group Project - Project 2
 * Created: 4/18
 * Last Modified: 4/18
 */

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
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu.backButtonActionPerformed();
            }
        });
        publicationOptionPanel.add(backButton);
        publicationOptionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        publicationOptionPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        Menu.getMenuFrame().add(publicationOptionPanel);
    }
}