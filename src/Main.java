import java.sql.*; 
import java.math.*; 
import java.io.*;
import java.nio.file.*;
import java.util.Scanner;
import oracle.jdbc.driver.*;
import org.apache.commons.cli.*;

class Main
{
    public static void main(String[] args) throws SQLException {

        Options options = new Options();

        Option input = new Option("i", "input", true, "input file path");
        input.setRequired(true);
        options.addOption(input);

        /*
        Option output = new Option("o", "output", true, "output file");
        output.setRequired(true);
        options.addOption(output);
         */

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null; //not a good practice, it serves it purpose 

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }

        String inputFilePath = cmd.getOptionValue("input");
        //String outputFilePath = cmd.getOptionValue("output");

        System.out.println(inputFilePath);
        //System.out.println(outputFilePath);

        

        // Load main menu
        Menu.createAndShowGUI();
        Driver.initDriver(inputFilePath);


        //Driver.closeConnection();
    }
}