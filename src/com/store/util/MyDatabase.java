package com.store.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class MyDatabase {
    private static final String URL = "jdbc:mysql://localhost:3306/smartphone_store";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi kết nối DB", e);
        }
    }
}