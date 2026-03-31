package com.store.dao;

import com.store.model.User;
import com.store.util.MyDatabase;

import java.sql.*;

public class UserDAO {

    public void save(User user) {
        String sql = "INSERT INTO users(username, email, password, full_name, role, phone, address) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection c = MyDatabase.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getFullName());
            ps.setString(5, user.getRole());
            ps.setString(6, user.getPhone());
            ps.setString(7, user.getAddress());

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Lỗi insert user");
        }
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection c = MyDatabase.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("full_name"),
                        rs.getString("role"),
                        rs.getString("phone"),
                        rs.getString("address")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}