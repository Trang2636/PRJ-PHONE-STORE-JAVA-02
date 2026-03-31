package com.store.service;

import com.store.dao.UserDAO;
import com.store.model.User;
import com.store.util.BCryptUtil;
import com.store.util.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthService {



    private UserDAO dao = new UserDAO();

    public User login(String email, String password) {
        User user = dao.findByEmail(email);

        if (user == null) return null;

        if (BCryptUtil.check(password, user.getPassword())) {
            return user;
        }

        return null;
    }

    public void register(String username, String email, String password,
                         String fullName, String phone, String address) {

        if (dao.findByEmail(email) != null) {
            throw new RuntimeException("Email đã tồn tại!");
        }

        String hash = BCryptUtil.hash(password);

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(hash);
        user.setFullName(fullName);
        user.setRole("CUSTOMER");
        user.setPhone(phone);
        user.setAddress(address);

        dao.save(user);
    }

//    public void check(String email){
//        String sql = "select * from domain where end = ?";
//        try (Connection c = MyDatabase.getConnection();
//             PreparedStatement ps = c.prepareStatement(sql)) {
//            ps.setString(1, email);
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
    }

