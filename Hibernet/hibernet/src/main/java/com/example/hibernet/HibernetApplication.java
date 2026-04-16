package com.example.hibernet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.Scanner;

public class HibernetApplication {

    static SessionFactory factory;
    static Session session;
    static Transaction tx;
    static Scanner sc = new Scanner(System.in);

    static {
        factory = new Configuration()
                .configure("hiber.cfg.xml")
                .addAnnotatedClass(Product.class)
                .buildSessionFactory();
    }

    public static void main(String[] args) {
        int choice = 0;

        while (choice != 5) {
            System.out.println("\n--- Product Management ---");
            System.out.println("1. Add Product");
            System.out.println("2. Search Product");
            System.out.println("3. Remove Product");
            System.out.println("4. Update Product");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1: addProduct(); break;
                case 2: searchProduct(); break;
                case 3: removeProduct(); break;
                case 4: updateProduct(); break;
                case 5: System.out.println("Exiting..."); break;
                default: System.out.println("Invalid choice!");
            }
        }
        factory.close();
    }

    public static void addProduct() {
        session = factory.openSession();
        tx = session.beginTransaction();

        Product p = new Product();
        System.out.print("Enter ID: ");
        p.setProductId(sc.nextInt());
        sc.nextLine(); // consume newline
        System.out.print("Enter Name: ");
        p.setProductname(sc.nextLine());
        System.out.print("Enter Price: ");
        p.setPrice(sc.nextDouble());

        session.persist(p);
        tx.commit();
        session.close();
        System.out.println("Product saved!");
    }

    

public static void searchProduct() {
        session = factory.openSession();
        System.out.print("Enter ID to search: ");
        int id = sc.nextInt();
        Product p = session.get(Product.class, id);

        if (p != null) {
            System.out.println("Found: " + p.getProductname() + " | Price: " + p.getPrice());
        } else {
            System.out.println("Product not found.");
        }
        session.close();
    }

    public static void removeProduct() {
        session = factory.openSession();
        tx = session.beginTransaction();
        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();
        Product p = session.get(Product.class, id);

        if (p != null) {
            session.remove(p);
            tx.commit();
            System.out.println("Deleted successfully.");
        } else {
            System.out.println("ID not found.");
        }
        session.close();
    }

    public static void updateProduct() {
        session = factory.openSession();
        tx = session.beginTransaction();
        System.out.print("Enter ID to update price: ");
        int id = sc.nextInt();
        Product p = session.get(Product.class, id);

        if (p != null) {
            System.out.print("Enter new price: ");
            p.setPrice(sc.nextDouble());
            session.merge(p); // merge is safer than update in modern Hibernate
            tx.commit();
            System.out.println("Price updated!");
        } else {
            System.out.println("ID not found.");
        }
        session.close();
    }
}