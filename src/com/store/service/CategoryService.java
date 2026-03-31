package com.store.service;

import com.store.dao.CategoryDAO;
import com.store.model.Category;
import java.util.List;

public class CategoryService {
    private CategoryDAO dao = new CategoryDAO();

    public List<Category> findAll() {

        return dao.findAll();
    }

    public void add(String name, String desc) {
        if (name == null) {
            System.out.println("Tên danh mục không được trống!");
            return;
        }

        name = name.trim();

        if (name.isEmpty()) {
            System.out.println("Tên danh mục không được trống!");
            return;
        }

        if (dao.existsByName(name)) {
            System.out.println("Tên danh mục đã tồn tại!");
            return;
        }

        dao.add(new Category(name, desc));
        System.out.println("Thêm thành công!");
    }

    public void update(int id, String name, String desc) {
        if (!dao.existsById(id)) {
            System.out.println("ID không tồn tại!");
            return;
        }

        if (name == null) {
            System.out.println("Tên không được trống!");
            return;
        }

        name = name.trim();

        if (name.isEmpty()) {
            System.out.println("Tên không được trống!");
            return;
        }

        if (dao.existsByName(name)) {
            System.out.println("Tên đã tồn tại!");
            return;
        }

        dao.update(new Category(id, name, desc, true));
        System.out.println("Cập nhật thành công!");
    }

    public void delete(int id) {
        if (!dao.existsById(id)) {
            System.out.println("ID không tồn tại!");
            return;
        }

        dao.delete(id);
        System.out.println("Đã ẩn danh mục!");
    }
}