package com.library;

import com.library.utility.HibernateUtil;
import com.library.DAO.LibraryDAO;
import com.library.service.LibraryService;
import com.library.entity.Book;
import com.library.entity.Member;
import java.util.Scanner;

public class LibraryApp {
    public static void main(String[] args) {
        HibernateUtil util = new HibernateUtil();
        LibraryDAO dao = new LibraryDAO(util.getSessionFactory());
        LibraryService service = new LibraryService(dao);
        
        Scanner sc = new Scanner(System.in);
        int choice = 0;

        while (choice != 4) {
            System.out.println("\n--- LIBRARY MANAGEMENT SYSTEM ---");
            System.out.println("1. Admin Login");
            System.out.println("2. Member Login");
            System.out.println("3. New Member Registration");
            System.out.println("4. Exit");
            System.out.print("Select Option: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> adminMenu(sc, service, dao);
                case 2 -> memberMenu(sc, service, dao);
                case 3 -> {
                    System.out.println("\n--- MEMBER REGISTRATION ---");
                    System.out.print("Enter ID: "); int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Enter Name: "); String name = sc.nextLine();
                    System.out.print("Enter Phone Number: "); String phone = sc.nextLine();
                    service.registerMember(id, name, phone);
                }
                case 4 -> {
                    util.closeFactory();
                    System.out.println("System Closed. Goodbye!");
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    static void adminMenu(Scanner sc, LibraryService service, LibraryDAO dao) {
        System.out.println("\n--- ADMIN PANEL ---");
        System.out.println("1. Add Book\n2. Remove Book\n3. Update Book\n4. Search Book\n5. Logout");
        System.out.print("Choice: ");
        int ch = sc.nextInt();
        switch (ch) {
            case 1 -> {
                System.out.print("Book ID: "); int id = sc.nextInt(); sc.nextLine();
                System.out.print("Book Name: "); String n = sc.nextLine();
                System.out.print("Author Name: "); String a = sc.nextLine();
                System.out.print("Price: "); double p = sc.nextDouble();
                service.addBook(id, n, a, p);
            }
            case 2 -> {
                System.out.print("Enter Book ID to remove: ");
                service.removeBook(sc.nextInt());
            }
            case 3 -> {
                System.out.print("Enter Book ID to update: "); int id = sc.nextInt(); sc.nextLine();
                System.out.print("Enter New Name: "); String n = sc.nextLine();
                System.out.print("Enter New Price: "); double p = sc.nextDouble();
                service.updateBookDetails(id, n, p);
            }
            case 4 -> {
                System.out.print("Enter Book ID: ");
                Book b = dao.findBookById(sc.nextInt());
                if (b != null) {
                    System.out.println("Found: " + b.getBookName() + " | Status: " + b.getStatus());
                } else System.out.println("Book not found.");
            }
        }
    }

    static void memberMenu(Scanner sc, LibraryService service, LibraryDAO dao) {
        System.out.print("Enter your Member ID: ");
        int mid = sc.nextInt();
        
        Member m = dao.findMemberById(mid);
        if (m == null) {
            System.out.println("Error: Access Denied. ID not registered.");
            return;
        }

        System.out.println("\nWelcome, " + m.getMemberName());
        System.out.println("1. Issue Book\n2. Return Book\n3. Search Book\n4. Logout");
        System.out.print("Choice: ");
        int ch = sc.nextInt();
        switch (ch) {
            case 1 -> {
                System.out.print("Enter Book ID to Issue: ");
                System.out.println(service.issueBook(sc.nextInt(), mid));
            }
            case 2 -> {
                System.out.print("Enter Book ID to Return: ");
                System.out.println(service.returnBook(sc.nextInt()));
            }
            case 3 -> {
                System.out.print("Enter Book ID to Search: ");
                Book b = dao.findBookById(sc.nextInt());
                if (b != null) {
                    System.out.println("Book: " + b.getBookName() + " | Status: " + b.getStatus());
                } else System.out.println("Book not found.");
            }
        }
    }
}