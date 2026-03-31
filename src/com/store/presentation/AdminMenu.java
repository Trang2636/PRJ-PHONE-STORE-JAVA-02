package com.store.presentation;

import com.store.service.OrderService;

import java.util.Scanner;

public class AdminMenu {
    private Scanner sc = new Scanner(System.in);
    private OrderService orderService = new OrderService();

    public void display() {
        while (true) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. Category");
            System.out.println("2. Product");
            System.out.println("3. Quản lý đơn hàng");
            System.out.println("4. Thống kê bán hàng");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> new CategoryMenu().display();
                case 2 -> new ProductMenu().display();
                case 3 -> manageOrders();
                case 4 -> orderService.top5BestSeller();
                case 0 -> { return; }
            }
        }
    }

    private void manageOrders() {
        while (true) {
            System.out.println("\n--- DANH SÁCH ĐƠN ---");
            orderService.getAllOrders();

            System.out.println("1. Cập nhật trạng thái");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");

            int choice = Integer.parseInt(sc.nextLine());

            if (choice == 0) return;

            if (choice == 1) {
                System.out.print("Nhập ID đơn: ");
                int id = Integer.parseInt(sc.nextLine());

                System.out.println("1. SHIPPING");
                System.out.println("2. DELIVERED");
                System.out.print("Chọn: ");

                int st = Integer.parseInt(sc.nextLine());

                String status = switch (st) {
                    case 1 -> "SHIPPING";
                    case 2 -> "DELIVERED";
                    default -> "PENDING";
                };

                orderService.updateOrderStatus(id, status);
                System.out.println("Cập nhật thành công!");
            }
        }
    }
}