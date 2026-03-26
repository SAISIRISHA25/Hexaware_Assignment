package com.example.dao;
import com.example.util.DBConnection;
import java.sql.*;

public class AdminDAO {
    public void addQuestion(String text, String a, String b, String c, String d, String ans) {
        String sql = "INSERT INTO Questions (questionText, optionA, optionB, optionC, optionD, correctAnswer) VALUES (?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, text);
            pst.setString(2, a);
            pst.setString(3, b);
            pst.setString(4, c);
            pst.setString(5, d);
            pst.setString(6, ans.toUpperCase());
            pst.executeUpdate();
            System.out.println("Question Added Successfully!");
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
    }

    public void updateQuestion(int id, String newText) {
        String sql = "UPDATE Questions SET questionText = ? WHERE questionId = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, newText);
            pst.setInt(2, id);
            int count = pst.executeUpdate();
            System.out.println(count > 0 ? "Update Success!" : "ID Not Found.");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void viewQuestions() {
        String sql = "SELECT * FROM Questions";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("\n[" + rs.getInt(1) + "] " + rs.getString(2));
                System.out.println("A) " + rs.getString(3) + " B) " + rs.getString(4));
                System.out.println("C) " + rs.getString(5) + " D) " + rs.getString(6));
                System.out.println("Correct: " + rs.getString(7));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}