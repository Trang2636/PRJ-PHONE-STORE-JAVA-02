package com.store.service;

import com.store.dao.ProductDAO;
import com.store.model.Product;

import java.util.List;

public class ProductService {
    private ProductDAO dao = new ProductDAO();

    public List<Product> getAll() {
        return dao.getAll();
    }

    public Product getById(int id) {
        if (id <= 0) return null;
        return dao.getById(id);
    }

    public void add(String name, String color, double price, int stock, int categoryId) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Tên không được trống!");
            return;
        }

        if (color == null || color.trim().isEmpty()) {
            System.out.println("Màu không được trống!");
            return;
        }

        if (price <= 0) {
            System.out.println("Giá phải > 0!");
            return;
        }

        if (stock < 0) {
            System.out.println("Stock phải >= 0!");
            return;
        }

        dao.add(new Product(0, name, color, price, stock, categoryId));
    }

    public void delete(int id) {
        if (id <= 0) {
            System.out.println("ID không hợp lệ!");
            return;
        }

        if (dao.getById(id) == null) {
            System.out.println("Không tồn tại sản phẩm!");
            return;
        }

        dao.delete(id);
    }

    public void update(Product p) {
        if (p == null) return;

        if (p.getName() == null || p.getName().trim().isEmpty()) {
            System.out.println("Tên không được trống!");
            return;
        }

        if (p.getColor() == null || p.getColor().trim().isEmpty()) {
            System.out.println("Màu không được trống!");
            return;
        }

        if (p.getPrice() <= 0) {
            System.out.println("Giá phải > 0!");
            return;
        }

        if (p.getStock() < 0) {
            System.out.println("Stock phải >= 0!");
            return;
        }

        dao.add(p);
    }

    public List<Product> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return dao.getAll();
        }
        return dao.search(keyword);
    }
    public List<Product> getByPage(int page, int size) {
        return dao.getByPage(page, size);
    }
    public int count() {
        return dao.count();
    }
}