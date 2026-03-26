package com.example.dao;
import com.example.util.DBConnection;
import java.sql.*;
import java.util.Scanner;

public class UserDAO {
    public void register(String name, String email) {
        String sql = "INSERT INTO Members (name, email) VALUES (?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, name);
            pst.setString(2, email);
            pst.executeUpdate();
            System.out.println("User Registered!");
        } catch (SQLException e) { System.out.println("Error while registering user: " + e.getMessage()); }
    }

public void giveExam(Scanner sc) { 
    int score = 0, total = 0;
    String sql = "SELECT * FROM Questions";
    try (Connection con = DBConnection.getConnection(); 
         Statement st = con.createStatement(); 
         ResultSet rs = st.executeQuery(sql)) {
        
        while(rs.next()) {
            total++;
            System.out.println("\nQ" + total + ": " + rs.getString(2));
            System.out.println("A) " + rs.getString("optionA"));
             System.out.println("B) " + rs.getString("optionB"));
             System.out.println("C) " + rs.getString("optionC"));
            System.out.println("D) " + rs.getString("optionD"));
             System.out.print("Your Answer (A/B/C/D): ");
              String ans = sc.nextLine().trim().toUpperCase();
            if(ans.equals(rs.getString(7))) score++;
        }
        System.out.println("\nFinal Score: " + score + "/" + total);
    } catch (SQLException e) { e.printStackTrace(); }
}
}