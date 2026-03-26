package com.example;
import com.example.dao.*;
import java.util.Scanner;

public class App {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        AdminDAO admin = new AdminDAO();
        UserDAO user = new UserDAO();

        while (true) {
            System.out.println("\n--- MAIN MENU ---\n1. Admin\n2. User\n3. Exit");
            int choice = getValidInt();

            if (choice == 1) {
                System.out.println("\n1. Add Question\n2. Update Question\n3. View All");
                int opt = getValidInt();
                if (opt == 1) {
                    System.out.print("Text: "); String q = sc.nextLine();
                    System.out.print("A: "); String a = sc.nextLine();
                    System.out.print("B: "); String b = sc.nextLine();
                    System.out.print("C: "); String c = sc.nextLine();
                    System.out.print("D: "); String d = sc.nextLine();
                    System.out.print("Ans (A/B/C/D): "); String ans = sc.nextLine();
                    admin.addQuestion(q, a, b, c, d, ans);
                } else if (opt == 2) {
                    System.out.print("ID: "); int id = getValidInt();
                    System.out.print("New Text: "); String text = sc.nextLine();
                    admin.updateQuestion(id, text);
                } else admin.viewQuestions();

            } else if (choice == 2) {
                System.out.println("\n1. Register\n2. Give Exam");
                int opt = getValidInt();
                if (opt == 1) {
                    System.out.print("Name: "); String n = sc.nextLine();
                    System.out.print("Email: "); String e = sc.nextLine();
                    user.register(n, e);
                } else user.giveExam(sc);
            } else break;
        }
    }

    private static int getValidInt() {
        while (!sc.hasNextInt()) {
            System.out.print("Enter a number: ");
            sc.next();
        }
        int val = sc.nextInt();
        sc.nextLine(); 
        return val;
    }
}