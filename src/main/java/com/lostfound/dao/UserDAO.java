package com.lostfound.dao;

import com.lostfound.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    /**
     * Validates if the provided credentials exist in the database.
     * @param userId The input User ID from the login page.
     * @param password The input Password from the login page.
     * @return true if credentials are valid, false otherwise.
     */
    public boolean validateUser(String userId, String password) {
        String query = "SELECT * FROM users WHERE user_id = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, userId);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Returns true if a matching record is found
            }

        } catch (Exception e) {
            System.err.println("Error during user validation in UserDAO:");
            e.printStackTrace();
            return false;
        }
    }
}