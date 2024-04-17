import java.sql.*;
import oracle.jdbc.driver.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.*;
import java.util.Scanner;

public class Driver
{
    private static Connection conn;

    private static String defaultFilePath;

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
            conn.close();
        } catch (SQLException sqle) {
            System.out.println("SQL Database connection failed to close.");
        }
        
    }

    public static boolean isConnected()
    {
        try {
            return !conn.isClosed();
        } catch (SQLException sqle) {
            return false;
        }
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
}