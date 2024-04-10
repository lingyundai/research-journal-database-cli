import java.sql.*; 
import java.math.*; 
import java.io.*;
import java.util.Scanner;
import oracle.jdbc.driver.*;

class Main
{
    public static void main(String[] args) throws SQLException {
        // Load Oracle driver 
        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        } catch (Exception e) {
            System.out.println("Failed to load JDBC/ODBC driver.");
            return;
        }
        
        Scanner reader = new Scanner(System.in);

        //Prompt user for username and password
        String user;
        String password;

        System.out.println();
        System.out.print("username: "); 
        user = reader.nextLine();

        System.out.println();
        System.out.print("password: ");
        password = reader.nextLine();

        reader.close();

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
            
        } catch (Exception e) {
            System.out.println("Could not connect to database with provided credentials");
        }
    }
}