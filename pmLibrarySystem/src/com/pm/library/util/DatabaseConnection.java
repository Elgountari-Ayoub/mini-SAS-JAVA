package com.pm.library.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection instance;

    // temp.Database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/librarySystemDB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private Connection connection;
    private DatabaseConnection() {

    }

    public static Connection getInstance() {
        if (instance == null) {
            instance = DatabaseConnection.getConnection(); // Create the instance if it doesn't exist
        }
        return instance;
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create a connection to the database
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            if (connection != null) {
                System.out.println("Connected to the database.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load JDBC driver: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("temp.Database connection error: " + e.getMessage());
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("temp.Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
}
