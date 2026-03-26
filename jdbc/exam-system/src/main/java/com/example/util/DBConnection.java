package com.example.util;
import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/exam_db";
    private static final String USER = "root";
    private static final String PASS = "root"; 

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver not found! Check your pom.xml.");
        }
        
        return DriverManager.getConnection(URL, USER, PASS);
    }
}