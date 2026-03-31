package com.store.dao;

import com.store.model.Category;
import com.store.util.MyDatabase;

import java.sql.*;
import java.util.*;

public class CategoryDAO {

    public List<Category> findAll() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM categories WHERE status = true ";

        try (Connection c = MyDatabase.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Category(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBoolean("status")
                ));
            }

        } catch (Exception e) {
            throw new RuntimeException("Lỗi findAll", e);
        }
    if(list.isEmpty()) {
        System.out.println("Chưa có sản phẩm nào ");
    }
        return list;
    }

    public boolean existsByName(String name) {
        String sql = "SELECT 1 FROM categories WHERE name = ? AND status = true";

        try (Connection c = MyDatabase.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            return false;
        }
    }

    public boolean existsById(int id) {
        String sql = "SELECT 1 FROM categories WHERE id = ? AND status = true";

        try (Connection c = MyDatabase.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            return false;
        }
    }

    public void add(Category c) {
        String sql = "INSERT INTO categories(name, description, status) VALUES (?, ?, true)";

        try (Connection conn = MyDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            ps.setString(2, c.getDescription());

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Lỗi add category", e);
        }
    }

    public void update(Category c) {
        String sql = "UPDATE categories SET name=?, description=? WHERE id=?";

        try (Connection conn = MyDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            ps.setString(2, c.getDescription());
            ps.setInt(3, c.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Lỗi update category", e);
        }
    }

    public void delete(int id) {
        String sql = "UPDATE categories SET status = false WHERE id = ?";

        try (Connection conn = MyDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Lỗi delete category", e);
        }
    }
}