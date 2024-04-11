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

        // Load Oracle driver 
        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        } catch (Exception e) {
            System.out.println("Failed to load JDBC/ODBC driver.");
            return;
        }

        Scanner reader = new Scanner(System.in);

                System.out.println();
        System.out.println("Please enter credentials for signing in to Oracle SQL Server.");

        //Prompt user for username and password
        String user;
        String password;

        System.out.println();
        System.out.print("username: "); 
        user = reader.nextLine();

        System.out.println();
        System.out.print("password: ");
        password = reader.nextLine();

        //Connect to the database
        try {
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu", 
                user, password);
            DatabaseMetaData dbmd=conn.getMetaData();

            System.out.println("Connected.");

            if (dbmd==null) {
                System.out.println("No database meta data");
            }
            else {
                System.out.println("Database Product Name: "+dbmd.getDatabaseProductName());
                System.out.println("Database Product Version: "+dbmd.getDatabaseProductVersion());
                System.out.println("Database Driver Name: "+dbmd.getDriverName());
                System.out.println("Database Driver Version: "+dbmd.getDriverVersion());
            }

            // Now get the user prompt for the .sql file
            System.out.println();
            System.out.print("Please enter directory for the .sql file you would like to load: ");
            String filename = reader.nextLine();
            Path filepath = Paths.get(filename);
            reader.close();

            // Perform checks on file
            // Some checks to perform:
            // if file is a .sql file
            // if file directory path is valid
            // if file is not empty
            if (filename == null || filepath == null ||
                !Files.exists(filepath) || !filename.contains(".sql")) {
                    System.out.println();
                    System.out.println("That filename entered did not work.");
                    System.out.println("Using default filepath for \"paper.sql\"");
                    System.out.println();
                    filename = inputFilePath;
                    filepath = Paths.get(filename);
            }
        } catch (Exception e) {
            System.out.println();
            System.out.println("Could not connect to database with provided credentials");
        }
    }
}