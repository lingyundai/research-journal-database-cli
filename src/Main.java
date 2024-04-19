/**
 * File: Main.java
 * Authors: Lauren Dennedy G01462079, Lingyun Dai G01464288
 * Class: CS 550 Database Systems
 * Assignment: Group Project - Project 2
 * Created: 4/10
 * Last Modified: 4/18
 */

import java.sql.*;
import org.apache.commons.cli.*;

import gui.Menu;
import sql.Driver;

class Main
{
    /*
     * The main method represents the entry point for the program.
     * The GUI view class is handled in Menu.java, and the jdbc
     * driver is managed in Driver.java
     */
    public static void main(String[] args) throws SQLException {

        // Use a command line parser from Apache commons to accept
        // a default file path as an argument to main
        Options options = new Options();

        Option input = new Option("i", "input", true, "input file path");
        input.setRequired(true);
        options.addOption(input);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);
            System.exit(1);
        }

        String inputFilePath = (cmd != null) ? cmd.getOptionValue("input") : "";
        System.out.println("\nInitializing Program with default file: " + inputFilePath);

        // Load main menu
        Menu.createAndShowGUI();

        // Start the jdbc driver in the Driver class
        Driver.initDriver(inputFilePath);

        // Driver is automatically closed in terminating states of the program
    }
}