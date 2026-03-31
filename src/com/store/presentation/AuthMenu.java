package com.store.presentation;

import com.store.model.User;
import com.store.service.AuthService;

import java.util.Scanner;
import java.util.regex.Pattern;

public class AuthMenu {
    private AuthService authService = new AuthService();
    private Scanner sc = new Scanner(System.in);

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\d{10}$");

    public void start() {
        while (true) {
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                Hệ thống Quản lý Shop Bán Điện Thoại Online                                           ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃        1.Đăng Nhập                     ┃           2. Đăng kí               ┃           0. Thoát                     ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.print("Mời bạn chọn chức năng: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> login();
                case 2 -> register();
                case 0 -> System.exit(0);
            }
        }
    }

    private void login() {
        String email;
        while (true) {
            System.out.print("Email: ");
            email = sc.nextLine().trim();

            if (!isEmpty(email) && isValidEmail(email)) break;
            System.out.println("Email không hợp lệ!");

        }

        String password;
        while (true) {
            System.out.print("Password: ");
            password = sc.nextLine().trim();
            if (!isEmpty(password)) break;
            System.out.println("Không được để trống!");
        }

        User user = authService.login(email, password);

        if (user != null) {
            if (user.getRole().equals("ADMIN")) {
                new AdminMenu().display();
            } else {
                new CustomerMenu().display(user);
            }
        } else {
            System.out.println("Sai tài khoản hoặc mật khẩu!");
        }
    }

    private void register() {
        String username;
        while (true) {
            System.out.print("Username: ");
            username = sc.nextLine().trim();
            if (!isEmpty(username)) break;
            System.out.println("Không được để trống!");
        }

        String email;
        while (true) {
            System.out.print("Email: ");
            email = sc.nextLine().trim();
            if (!isEmpty(email) && isValidEmail(email)) break;
            System.out.println("Email không hợp lệ!");
        }

        String password;
        while (true) {
            System.out.print("Password: ");
            password = sc.nextLine().trim();
            if (!isEmpty(password)) break;
            System.out.println("Không được để trống!");
        }

        String fullName;
        while (true) {
            System.out.print("Full name: ");
            fullName = sc.nextLine().trim();
            if (!isEmpty(fullName)) break;
            System.out.println("Không được để trống!");
        }

        String phone;
        while (true) {
            System.out.print("Phone: ");
            phone = sc.nextLine().trim();
            if (!isEmpty(phone) && isValidPhone(phone)) break;
            System.out.println("SĐT phải 10 số!");
        }

        String address;
        while (true) {
            System.out.print("Address: ");
            address = sc.nextLine().trim();
            if (!isEmpty(address)) break;
            System.out.println("Không được để trống!");
        }

        try {
            authService.register(username, email, password, fullName, phone, address);
            System.out.println("Đăng ký thành công!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private boolean isValidPhone(String phone) {
        return PHONE_PATTERN.matcher(phone).matches();
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}