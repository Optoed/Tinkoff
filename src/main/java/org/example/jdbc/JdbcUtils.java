package org.example.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcUtils {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/urlDatabase";

    private static Connection connection;

    public static boolean createConnection() {
        try {
            //пользователь - postgres, пароль - отсутствует
            connection = DriverManager.getConnection(DB_URL, "postgres", "");
            return true;
        } catch (Exception ex) {
            System.out.println("Error occurred while connection to database: " + ex.getMessage());
        }
        return false;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (Exception ex) {
            System.out.println("Error occurred while closing connection to database: " + ex.getMessage());
        }
    }
}
