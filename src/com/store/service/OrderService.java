package com.store.service;

import com.store.dao.OrderDAO;
import com.store.model.CartItem;
import com.store.model.User;

import java.util.List;

public class OrderService {
    private OrderDAO orderDAO = new OrderDAO();

    public boolean placeOrder(User user, List<CartItem> cart, double total) {
        if (user == null || cart == null || cart.isEmpty() || total <= 0) return false;
        return orderDAO.saveOrder(user, cart, total);
    }

    public void getAllOrders() {
        orderDAO.getAllOrders();
    }

    public void updateOrderStatus(int id, String status) {
        orderDAO.updateStatus(id, status);
    }

    public void getOrdersByUser(int userId) {
        orderDAO.getOrdersByUser(userId);
    }

    public void top5BestSeller() {
        orderDAO.top5BestSeller();
    }
}