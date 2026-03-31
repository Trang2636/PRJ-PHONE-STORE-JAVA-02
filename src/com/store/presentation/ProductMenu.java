package com.store.presentation;

import com.store.model.Product;
import com.store.service.ProductService;

import java.util.List;
import java.util.Scanner;

public class ProductMenu {
    static Scanner sc = new Scanner(System.in);
    static ProductService service = new ProductService();

    public static void display() {
        while (true) {
            System.out.println("\n========= PRODUCT MENU =========");
            System.out.println("1. Xem sản phẩm");
            System.out.println("2. Thêm sản phẩm");
            System.out.println("3. Xóa sản phẩm");
            System.out.println("4. Sửa sản phẩm");
            System.out.println("5. Tìm kiếm");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:
                    int page = 1;
                    int pageSize = 5;

                    while (true) {
                        List<Product> list = service.getByPage(page, pageSize);
                        int total = service.count();
                        int totalPage = (int) Math.ceil((double) total / pageSize);

                        System.out.println("\n===== TRANG " + page + "/" + totalPage + " =====");

                        System.out.printf("%-5s | %-20s | %-10s | %-10s | %-10s | %-5s\n",
                                "ID", "Name", "Color", "Price", "Stock", "CatID");
                        System.out.println("---------------------------------------------------------------------");

                        list.forEach(System.out::println);

                        System.out.println("\nN: Next | P: Prev | 0: Thoát");
                        String nav = sc.nextLine();

                        if (nav.equalsIgnoreCase("N")) {
                            if (page < totalPage) page++;
                            else System.out.println("Trang cuối rồi!");
                        } else if (nav.equalsIgnoreCase("P")) {
                            if (page > 1) page--;
                            else System.out.println("Trang đầu rồi!");
                        } else if (nav.equals("0")) {
                            break;
                        }
                    }
                    break;

                case 2:
                    System.out.print("Tên: ");
                    String name = sc.nextLine();

                    System.out.print("Màu: ");
                    String color = sc.nextLine();

                    System.out.print("Giá: ");
                    double price = Double.parseDouble(sc.nextLine());

                    System.out.print("Stock: ");
                    int stock = Integer.parseInt(sc.nextLine());

                    System.out.print("Category ID: ");
                    int cid = Integer.parseInt(sc.nextLine());

                    service.add(name,color, price, stock, cid);
                    System.out.println("✔ Thêm thành công!");
                    break;

                case 3:
                    System.out.print("Nhập ID cần xóa: ");
                    int idDelete = Integer.parseInt(sc.nextLine());

                    System.out.print("Bạn có chắc muốn xóa? (Y/N): ");
                    String confirm = sc.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        service.delete(idDelete);
                        System.out.println("✔ Xóa thành công!");
                    } else {
                        System.out.println("Đã hủy!");
                    }
                    break;

                case 4:
                    System.out.print("ID: ");
                    int idUpdate = Integer.parseInt(sc.nextLine());

                    System.out.print("Tên: ");
                    String newName = sc.nextLine();

                    System.out.print("Màu: ");
                    String newColor = sc.nextLine();

                    System.out.print("Giá: ");
                    double newPrice = Double.parseDouble(sc.nextLine());

                    System.out.print("Stock: ");
                    int newStock = Integer.parseInt(sc.nextLine());

                    System.out.print("Category ID: ");
                    int newCid = Integer.parseInt(sc.nextLine());

                    Product p = new Product(idUpdate, newName, newColor, newPrice, newStock, newCid);
                    service.update(p);

                    System.out.println("✔ Cập nhật thành công!");
                    break;

                case 5:
                    System.out.print("Nhập tên: ");
                    String keyword = sc.nextLine();

                    List<Product> result = service.search(keyword);

                    if (result.isEmpty()) {
                        System.out.println("Không tìm thấy!");
                    } else {
                        System.out.printf("%-5s | %-20s | %-10s | %-10s | %-10s | %-5s\n",
                                "ID", "Name", "Color", "Price", "Stock", "CatID");
                        System.out.println("---------------------------------------------------------------------");

                        result.forEach(System.out::println);
                    }
                    break;

                case 0:
                    return;
            }
        }
    }
}