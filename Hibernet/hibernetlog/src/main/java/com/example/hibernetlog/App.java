package com.example.hibernetlog;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.Scanner;

public class App {
    
    private static final SessionFactory factory;
    private static final Scanner sc = new Scanner(System.in);

    static {
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

    public static void main(String[] args) {
        int choice = 0;
        while (choice != 7) {
            System.out.println("\n--- USER MANAGEMENT SYSTEM ---");
            System.out.println("1.SignUp 2.SignIn 3.ForgotPwd 4.UpdatePwd 5.Search 6.Delete 7.Exit");
            System.out.print("Select Option: ");
            choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1 -> signUp();
                case 2 -> signIn();
                case 3 -> forgotPassword();
                case 4 -> updatePassword();
                case 5 -> searchUser();
                case 6 -> deleteUser();
                case 7 -> System.out.println("Closing...");
            }
        }
        factory.close();
    }

    static void signUp() {
        try (Session s = factory.openSession()) {
            Transaction tx = s.beginTransaction();
            User u = new User();
            System.out.print("ID: "); u.setId(sc.nextInt()); sc.nextLine();
            System.out.print("Name: "); u.setUsername(sc.nextLine());
            System.out.print("Password: "); u.setPassword(sc.nextLine());
            System.out.print("Email: "); u.setEmail(sc.nextLine());
            s.persist(u);
            tx.commit();
            System.out.println("Success!");
        }
    }
    static void signIn() {
        try (Session s = factory.openSession()) {
            System.out.print("ID: "); int id = sc.nextInt(); sc.nextLine();
            System.out.print("Password: "); String p = sc.nextLine();
            User u = s.get(User.class, id);
            if (u != null && u.getPassword().equals(p)) 
                System.out.println("Logged in as: " + u.getUsername());
            else System.out.println("Invalid ID or Password.");
        }
    }

    static void forgotPassword() {
    try (Session s = factory.openSession()) {
        System.out.println("--- Password Recovery ---");
        System.out.print("Enter User ID: "); 
        int id = sc.nextInt();
        sc.nextLine(); 
        
        System.out.print("Enter Registered Email: "); 
        String inputEmail = sc.nextLine();

        User u = s.get(User.class, id);

        if (u != null && u.getEmail().equalsIgnoreCase(inputEmail)) {
            String pwd = u.getPassword();
            
            String hint = pwd.charAt(0) + "****" + pwd.charAt(pwd.length() - 1);
            
            System.out.println("Verification Successful!");
            System.out.println("Your Password Hint is: " + hint);
            System.out.println("Would you like to Update it now? (Choose Option 4 from main menu)");
        } else {
            System.out.println("Error: User details do not match our records.");
        }
    }
}

    static void updatePassword() {
    try (Session s = factory.openSession()) {
        System.out.println("--- Update Password ---");
        System.out.print("Enter User ID: "); 
        int id = sc.nextInt(); 
        sc.nextLine(); 

        
        User u = s.get(User.class, id);

        if (u != null) {
            System.out.print("Enter Current Password: ");
            String currentPwd = sc.nextLine();

            
            if (u.getPassword().equals(currentPwd)) {
                Transaction tx = s.beginTransaction();
                
                System.out.print("Enter New Password: ");
                String newPwd = sc.nextLine();
                
                
                u.setPassword(newPwd);
                s.merge(u); 
                
                tx.commit();
                System.out.println(">>> Password Updated Successfully!");
            } else {
                System.out.println(">>> Error: Current password does not match.");
            }
        } else {
            System.out.println(">>> Error: User ID not found.");
        }
    }
}

    static void searchUser() {
        try (Session s = factory.openSession()) {
            System.out.print("ID: "); int id = sc.nextInt(); sc.nextLine();
            System.out.print("Password: "); String p = sc.nextLine();
            User u = s.get(User.class, id);
            if (u != null && u.getPassword().equals(p))
                System.out.println("Details: " + u.getUsername() + " | " + u.getEmail());
            else System.out.println("Access Denied.");
        }
    }

    static void deleteUser() {
        try (Session s = factory.openSession()) {
            Transaction tx = s.beginTransaction();
            System.out.print("ID: "); int id = sc.nextInt(); sc.nextLine();
            System.out.print("Password: "); String p = sc.nextLine();
            User u = s.get(User.class, id);
            if (u != null && u.getPassword().equals(p)) {
                s.remove(u);
                tx.commit();
                System.out.println("Account Deleted.");
            } else System.out.println("Failed.");
        }
    }
}



    