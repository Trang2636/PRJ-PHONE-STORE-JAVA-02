package com.store.dao;

import com.store.model.Product;
import com.store.util.MyDatabase;

import java.sql.*;
import java.util.*;

public class ProductDAO {

    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection c = MyDatabase.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("color"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getInt("category_id")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Product getById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";

        try (Connection c = MyDatabase.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("color"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getInt("category_id")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void add(Product p) {
        String sql = "INSERT INTO products(name, color, price, stock, category_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection c = MyDatabase.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, p.getName());
            ps.setString(2, p.getColor());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4, p.getStock());
            ps.setInt(5, p.getCategoryId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection c = MyDatabase.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Product> search(String keyword) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ?";

        try (Connection c = MyDatabase.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("color"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getInt("category_id")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public int count() {
        String sql = "SELECT COUNT(*) FROM products";

        try (Connection c = MyDatabase.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<Product> getByPage(int page, int pageSize) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products LIMIT ? OFFSET ?";

        try (Connection c = MyDatabase.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            int offset = (page - 1) * pageSize;

            ps.setInt(1, pageSize);
            ps.setInt(2, offset);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("color"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getInt("category_id")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}