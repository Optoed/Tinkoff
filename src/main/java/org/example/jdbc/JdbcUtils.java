package org.example.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;

@Configuration
public class JdbcUtils {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/urlDatabase_http";

    @Bean(value = "connection")
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, "postgres", "my_password");
        } catch (Exception ex) {
            System.out.println("Error occurred while connection to database: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
