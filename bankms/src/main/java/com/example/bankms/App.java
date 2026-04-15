package com.example.bankms;


import java.util.Scanner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        CustomerService service = (CustomerService) context.getBean("customerService");

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== BANK CUSTOMER MANAGEMENT SYSTEM =====");
            System.out.println("1. Add New Customer");
            System.out.println("2. Deposit Amount");
            System.out.println("3. Withdraw Amount");
            System.out.println("4. Transfer Amount");
            System.out.println("5. Show All Customers");
            System.out.println("6. Enquire Customer Details");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    System.out.print("Enter Customer ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Customer Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Account Number: ");
                    long accountNo = sc.nextLong();

                    System.out.print("Enter Initial Balance: ");
                    double balance = sc.nextDouble();

                    Customer c = new Customer(id, name, accountNo, balance);
                    service.addCustomer(c);
                    break;

                case 2:
                    System.out.print("Enter Account Number: ");
                    long depAcc = sc.nextLong();
                    System.out.print("Enter Deposit Amount: ");
                    double depAmt = sc.nextDouble();
                    service.depositAmount(depAcc, depAmt);
                    break;

                case 3:
                    System.out.print("Enter Account Number: ");
                    long withAcc = sc.nextLong();
                    System.out.print("Enter Withdraw Amount: ");
                    double withAmt = sc.nextDouble();
                    service.withdrawAmount(withAcc, withAmt);
                    break;

                case 4:
                    System.out.print("Enter Sender Account Number: ");
                    long fromAcc = sc.nextLong();
                    System.out.print("Enter Receiver Account Number: ");
                    long toAcc = sc.nextLong();
                    System.out.print("Enter Transfer Amount: ");
                    double transAmt = sc.nextDouble();
                    service.transferAmount(fromAcc, toAcc, transAmt);
                    break;

                case 5:
                    service.showAllCustomers();
                    break;

                case 6:
                    System.out.print("Enter Account Number: ");
                    long enqAcc = sc.nextLong();
                    service.enquireCustomer(enqAcc);
                    break;

                case 7:
                    System.out.println("Exiting application...");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}