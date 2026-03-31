package com.store.presentation;

import com.store.dao.CategoryDAO;
import com.store.model.Category;
import com.store.service.CategoryService;

import java.util.Scanner;

import java.util.List;
import java.util.Scanner;

import static com.store.presentation.ProductMenu.service;

public class CategoryMenu {
    private CategoryService service = new CategoryService();
    private Scanner sc = new Scanner(System.in);

    public void display() {
        while (true) {
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ ┓");
            System.out.println("┃                                                 ACATEGORY MENU                                                                                                                         ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("┃                                        ┃                                    ┃                                        ┃                                ┃                                ┃");
            System.out.println("┃        1. Xem danh mục sản phẩm        ┃         2. Thêm danh mục           ┃           3. Sửa danh mục              ┃     4.Xóa                      ┃       0.Thoát                  ┃");
            System.out.println("┃                                        ┃                                    ┃                                        ┃                                ┃                                ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("Mời nhập vào lựa chọn: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    List<Category> list = service.findAll();
                    int num=0;
                    for (Category c : list) {
                        System.out.println(c);
                        num++;
                    }

                    System.out.printf(" %-5s |%-5s | %-20s | %-30s\n","STT", "ID", "Name", "Description");
                    System.out.println("-------------------------------------------------------------");


                    break;

                case 2:
                    System.out.print("Tên: ");
                    String name = sc.nextLine();
                    System.out.print("Mô tả: ");
                    String desc = sc.nextLine();
                    service.add(name,desc);
                    break;

                case 3:
                    System.out.print("ID: ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.print("Tên mới: ");
                    String newName = sc.nextLine();
                    System.out.print("Mô tả mới: ");
                    String newDesc = sc.nextLine();
                    service.update(id,newName,newDesc);
                    break;

                case 4:
                    System.out.print("ID: ");
                    int deleteId = Integer.parseInt(sc.nextLine());
                    service.delete(deleteId);
                    break;

                case 0:
                    return;
            }
        }
    }
}