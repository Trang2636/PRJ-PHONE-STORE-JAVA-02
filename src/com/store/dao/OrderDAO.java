package com.store.dao;

import com.store.model.CartItem;
import com.store.model.User;
import com.store.util.MyDatabase;

import java.sql.*;
import java.util.List;

public class OrderDAO {

    public boolean saveOrder(User user, List<CartItem> cart, double totalAmount) {
        Connection conn = null;

        try {
            conn = MyDatabase.getConnection();
            conn.setAutoCommit(false);

            String sqlOrder = "INSERT INTO orders (user_id, total, status, ship_address) VALUES (?, ?, ?, ?)";
            PreparedStatement psOrder = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS);

            psOrder.setInt(1, user.getId());
            psOrder.setDouble(2, totalAmount);
            psOrder.setString(3, "PENDING");
            psOrder.setString(4, user.getAddress());
            psOrder.executeUpdate();

            ResultSet rs = psOrder.getGeneratedKeys();
            int orderId = 0;
            if (rs.next()) orderId = rs.getInt(1);

            String sqlDetail = "INSERT INTO order_details (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
            String sqlUpdateStock = "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?";

            PreparedStatement psDetail = conn.prepareStatement(sqlDetail);
            PreparedStatement psStock = conn.prepareStatement(sqlUpdateStock);

            for (CartItem item : cart) {

                psDetail.setInt(1, orderId);
                psDetail.setInt(2, item.getProduct().getId());
                psDetail.setInt(3, item.getQuantity());
                psDetail.setDouble(4, item.getProduct().getPrice());
                psDetail.addBatch();

                psStock.setInt(1, item.getQuantity());
                psStock.setInt(2, item.getProduct().getId());
                psStock.setInt(3, item.getQuantity());

                int updated = psStock.executeUpdate();
                if (updated == 0) {
                    throw new SQLException("Không đủ hàng!");
                }
            }

            psDetail.executeBatch();

            conn.commit();
            return true;

        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return false;

        } finally {
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }

    public void getAllOrders() {
        String sql = "SELECT o.id, u.username, o.total, o.status, o.created_at " +
                "FROM orders o JOIN users u ON o.user_id = u.id";

        try (Connection c = MyDatabase.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("username") + " | " +
                                rs.getDouble("total") + " | " +
                                rs.getString("status") + " | " +
                                rs.getTimestamp("created_at")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";

        try (Connection c = MyDatabase.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, orderId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getOrdersByUser(int userId) {
        String sql = "SELECT * FROM orders WHERE user_id = ?";

        try (Connection c = MyDatabase.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getDouble("total") + " | " +
                                rs.getString("status") + " | " +
                                rs.getTimestamp("created_at")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void top5BestSeller() {
        String sql = "SELECT p.name, SUM(od.quantity) AS total_sold " +
                "FROM order_details od " +
                "JOIN products p ON od.product_id = p.id " +
                "GROUP BY od.product_id " +
                "ORDER BY total_sold DESC LIMIT 5";

        try (Connection c = MyDatabase.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n--- TOP 5 SẢN PHẨM BÁN CHẠY ---");

            while (rs.next()) {
                System.out.println(
                        rs.getString("name") + " | Đã bán: " + rs.getInt("total_sold")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}