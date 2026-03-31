package com.store.presentation;

import com.store.model.CartItem;
import com.store.model.Product;
import com.store.model.User;
import com.store.service.OrderService;
import com.store.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerMenu {
    private Scanner sc = new Scanner(System.in);
    private ProductService productService = new ProductService();
    private OrderService orderService = new OrderService();
    private List<CartItem> cart = new ArrayList<>();

    public void display(User user) {
        while (true) {
            System.out.println("\n=== SMARTPHONE STORE - CUSTOMER: " + user.getUsername().toUpperCase() + " ===");
            System.out.println("1. Xem & Mua sản phẩm");
            System.out.println("2. Tìm kiếm sản phẩm");
            System.out.println("3. Kiểm tra giỏ hàng (" + cart.size() + " mục)");
            System.out.println("4. Thanh toán");
            System.out.println("5.Xem lịch sử mua hàng");
            System.out.println("0. Đăng xuất");
            System.out.print("Chọn chức năng: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1 -> showProducts();
                    case 2 -> searchProduct();
                    case 3 -> showCart();
                    case 4 -> handleCheckout(user);
                    case 5 -> orderService.getOrdersByUser(user.getId());
                    case 0 -> { return; }
                    default -> System.out.println("Lựa chọn sai!");
                }
            } catch (Exception e) {
                System.out.println("Lỗi nhập liệu!");
            }
        }
    }

    private void showProducts() {
        List<Product> list = productService.getAll();
        System.out.printf("\n%-5s %-25s %-15s %-10s\n", "ID", "Tên sản phẩm", "Giá", "Kho");

        list.forEach(p -> {
            if (p.getStock() > 0) {
                System.out.printf("%-5d %-25s %-15.0f %-10d\n",
                        p.getId(), p.getName(), p.getPrice(), p.getStock());
            }
        });

        System.out.print("\nNhập ID sản phẩm để thêm vào giỏ (0 để thoát): ");

        int id;
        try {
            id = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            return;
        }

        if (id == 0) return;

        Product p = productService.getById(id);
        if (p == null) return;

        System.out.print("Nhập số lượng: ");
        int qty;

        try {
            qty = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            return;
        }

        if (qty <= 0 || qty > p.getStock()) {
            System.out.println("Số lượng không hợp lệ!");
            return;
        }

        boolean found = false;
        for (CartItem item : cart) {
            if (item.getProduct().getId() == p.getId()) {
                item.setQuantity(item.getQuantity() + qty);
                found = true;
                break;
            }
        }

        if (!found) {
            cart.add(new CartItem(p, qty));
        }

        System.out.println("Đã thêm vào giỏ!");
    }

    private void showCart() {
        if (cart.isEmpty()) {
            System.out.println("Giỏ hàng trống!");
            return;
        }

        double total = 0;
        System.out.println("\n--- GIỎ HÀNG ---");

        for (CartItem item : cart) {
            System.out.printf("- %s | SL: %d | %.0f\n",
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getSubTotal());
            total += item.getSubTotal();
        }

        System.out.println("Tổng: " + total);
    }

    private void handleCheckout(User user) {
        if (cart.isEmpty()) {
            System.out.println("Giỏ hàng trống!");
            return;
        }

        showCart();
        System.out.print("Xác nhận thanh toán (Y/N): ");

        if (!sc.nextLine().equalsIgnoreCase("Y")) return;

        double total = cart.stream().mapToDouble(CartItem::getSubTotal).sum();

        if (orderService.placeOrder(user, cart, total)) {
            System.out.println("Đặt hàng thành công!");
            cart.clear();
        } else {
            System.out.println("Đặt hàng thất bại!");
        }
    }

    private void searchProduct() {
        System.out.print("Nhập từ khóa: ");
        String key = sc.nextLine();

        List<Product> list = productService.search(key);

        System.out.printf("\n%-5s %-25s %-10s\n", "ID", "Tên", "Giá");
        list.forEach(p ->
                System.out.printf("%-5d %-25s %-10.0f\n",
                        p.getId(), p.getName(), p.getPrice()));
    }
}