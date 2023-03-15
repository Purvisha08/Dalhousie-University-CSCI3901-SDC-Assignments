/**
 * {@code DbConnecetion}
 *
 * This calss is used to extablisk connecteion with the database. This class contains username,
 * password and databse name which hepls to connect with the database. This class fetach
 * the username, password and database name from the login.properties file
 *
 * @author Purvisha Patel (B00912611)
 * Created on 2022-04-06
 * @version 1.0.0
 * @since 1.0,0
 */

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DbConnection {

    /**
     * {@code createDbConnection} This Method is used to make connection with the database
     *
     * @throws Exception
     *         If there is any error while connecting with the database
     *
     * @return Return null if there is any error while connecting with the databse
     *
     */
    public Connection createDbConnection (){
        try {
            Properties properties = new Properties(); // properties object
            properties.load(new FileInputStream("src/login.properties")); // Location of login.properties file
            String USER = properties.getProperty("USERNAME"); // username of the database
            String PASSWORD = properties.getProperty("PASSWORD"); // password for the database
            String DATABASE_NAME = properties.getProperty("DATABASE_NAME"); // dataabse name
            String CONNECTION_STRING = "jdbc:mysql://localhost:3306/" + DATABASE_NAME + "?useSSL=false"; // connection string to connect to the database
            return DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
        } catch (Exception e) {
            // Retrun null if there is any error while connecteing with the database
            System.out.println("Error Message - " + e.getMessage());
            return null;
        }
    }
}
