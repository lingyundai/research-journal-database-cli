/**
 * File: Driver.java
 * Authors: Lauren Dennedy G01462079, Lin Dai _________
 * Class: CS 550 Database Systems
 * Assignment: Group Project - Project 2
 * Created: 4/17
 * Last Modified: 4/18
 */

import java.sql.*;
import oracle.jdbc.driver.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver
{
    private static Connection conn;

    private static String defaultFilePath;

    private static String GET_PUBLICATIONS_TABLE_QUERY = "SELECT * FROM PUBLICATIONS";
    private static String GET_AUTHORS_TABLE_QUERY = "SELECT * FROM AUTHORS";

    public static void initDriver()
    {
        // Load Oracle driver 
        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        } catch (Exception e) {
            System.out.println("Failed to load JDBC/ODBC driver.");
            return;
        }
    }

    // Overloaded method receiving a default filepath
    public static void initDriver(String fp) 
    {
        // Load Oracle driver 
        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            defaultFilePath = fp;
        } catch (Exception e) {
            System.out.println("Failed to load JDBC/ODBC driver.");
            return;
        }
    }

    public static void connectToDB(String username, String password)
    {
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu", 
                username, password);
            System.out.println("Connected.");
        } catch (Exception e) {
            System.out.println();
            System.out.println("Could not connect to database with provided credentials");
        }
    }

    public static void closeConnection()
    {
        try {
            System.out.println("\nClosing SQL Database connection...");
            conn.close();
            System.out.println("\nSQL Database connection successfully closed.");
        } catch (SQLException sqle) {
            System.out.println("SQL Database connection failed to close.");
        }
    }

    public static boolean isConnected()
    {
        try {
            if (conn != null) {
                return !conn.isClosed();
            }
        } catch (SQLException sqle) {
            return false;
        }

        return false;
    }

    public static boolean loadSqlFromFile(String filename) 
    {
        Path filepath = Paths.get(filename);

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
                if (defaultFilePath != null) {
                    filename = defaultFilePath;
                    filepath = Paths.get(filename);
                }
        }
        return readSqlScript(filename);
    }

    public static boolean readSqlScript(String filename)
    {
        try {
            FileInputStream inputFileStream = new FileInputStream(filename);
            Scanner reader = new Scanner(inputFileStream);
            while (reader.hasNextLine()) {
                try (Statement statement = conn.createStatement()) {
                    String line = reader.nextLine();
                    line = line.substring(0, line.length()-1);

                    // Debug only:
                    //System.out.println(line);
                    //current++;
                    //System.out.println(statement.toString());

                    statement.executeQuery(line);
                    statement.close();
                } catch (SQLException e) {
                    System.out.println(e);
                    System.out.println("Could not successfully execute last command");
                }
            }
            reader.close();
            System.out.println("Finished executing all sql statements");
            
            return true;
        } catch (FileNotFoundException e) {
            System.out.println(e);
            return false;
        }
    }

    private static ResultSet getPublicationsTable()
    {
        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(GET_PUBLICATIONS_TABLE_QUERY);
            return result;
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    public static ArrayList<ArrayList<String>> getPublicationsData()
    {
        ArrayList<ArrayList<String>> publicationsData = new ArrayList<ArrayList<String>>();

        ResultSet publicationsResult = Driver.getPublicationsTable();

        try {
            if (publicationsResult != null) {
                System.out.println("\nGetting Publications Table Data...");
                while (publicationsResult.next()) {
                    String pubID = publicationsResult.getString("PUBLICATIONID");
                    String year = publicationsResult.getString("YEAR");
                    String type = publicationsResult.getString("TYPE");
                    String title = publicationsResult.getString("TITLE");
                    String summary = publicationsResult.getString("SUMMARY");
    
                    ArrayList<String> row = new ArrayList<>();
                    row.add(pubID);
                    row.add(year);
                    row.add(type);
                    row.add(title);
                    row.add(summary);

                    publicationsData.add(row);
                }
                publicationsResult.getStatement().close();
                System.out.println("\nPublications Table Data Retrieval Complete!");
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return publicationsData;
    }

    private static ResultSet getAuthorsTable()
    {
        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(GET_AUTHORS_TABLE_QUERY);
            return result;
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    public static ArrayList<ArrayList<String>> getAuthorsData()
    {
        ArrayList<ArrayList<String>> authorsData = new ArrayList<ArrayList<String>>();

        ResultSet authorsResult = Driver.getAuthorsTable();

        try {
            if (authorsResult != null) {
                System.out.println("\nGetting Authors Table Data...");
                while (authorsResult.next()) {
                    String pubID = authorsResult.getString("PUBLICATIONID");
                    String author = authorsResult.getString("AUTHOR");
    
                    ArrayList<String> row = new ArrayList<>();
                    row.add(pubID);
                    row.add(author);

                    authorsData.add(row);
                }
                authorsResult.getStatement().close();
                System.out.println("\nAuthors Table Data Retrieval Complete!");
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }

        return authorsData;
    }
}