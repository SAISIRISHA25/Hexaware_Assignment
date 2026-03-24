package EmployeeManagement;

import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeManagement {
    static ArrayList<Employee> employees = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println();
            System.out.println("===== EMPLOYEE MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Temporary Employee");
            System.out.println("2. Add Permanent Employee");
            System.out.println("3. Calculate Salary");
            System.out.println("4. Display All Employees");
            System.out.println("5. Search Employee by ID");
            System.out.println("6. Update Employee Salary Details");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addTemporaryEmployee();
                    break;
                case 2:
                    addPermanentEmployee();
                    break;
                case 3:
                    calculateSalary();
                    break;
                case 4:
                    displayAllEmployees();
                    break;
                case 5:
                    searchEmployee();
                    break;
                case 6:
                    updateEmployee();
                    break;
                case 7:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 7);
    }

    static void addTemporaryEmployee() {
        System.out.println();
        System.out.println("----- Add Temporary Employee -----");
        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (findEmployee(id) != null) {
            System.out.println("Employee ID already exists.");
            return;
        }

        System.out.print("Enter Employee Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Hours Worked: ");
        int hoursWorked = sc.nextInt();
        System.out.print("Enter Rate Per Hour: ");
        double ratePerHour = sc.nextDouble();

        employees.add(new TemporaryEmployee(id, name, hoursWorked, ratePerHour));
        System.out.println("Temporary Employee added successfully.");
    }

    static void addPermanentEmployee() {
        System.out.println();
        System.out.println("----- Add Permanent Employee -----");
        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (findEmployee(id) != null) {
            System.out.println("Employee ID already exists.");
            return;
        }

        System.out.print("Enter Employee Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Basic Salary: ");
        double basicSalary = sc.nextDouble();
        System.out.print("Enter Bonus: ");
        double bonus = sc.nextDouble();

        employees.add(new PermanentEmployee(id, name, basicSalary, bonus));
        System.out.println("Permanent Employee added successfully.");
    }

    static void calculateSalary() {
        System.out.println();
        System.out.println("----- Calculate Salary -----");
        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();

        Employee e = findEmployee(id);
        if (e != null) {
            System.out.println("Employee Name      : " + e.getName());
            System.out.println("Calculated Salary : " + e.calculateSalary());
        } else {
            System.out.println("Employee not found.");
        }
    }

    static void displayAllEmployees() {
        System.out.println();
        System.out.println("----- Employee Details -----");
        if (employees.isEmpty()) {
            System.out.println("No employees available.");
            return;
        }

        for (Employee e : employees) {
            e.displayDetails();
        }
    }

    static void searchEmployee() {
        System.out.println();
        System.out.println("----- Search Employee -----");
        System.out.print("Enter Employee ID to search: ");
        int id = sc.nextInt();

        Employee e = findEmployee(id);
        if (e != null) {
            System.out.println("Employee Found:");
            e.displayDetails();
        } else {
            System.out.println("Employee not found.");
        }
    }

    static void updateEmployee() {
        System.out.println();
        System.out.println("----- Update Employee Salary Details -----");
        System.out.print("Enter Employee ID to update: ");
        int id = sc.nextInt();

        Employee e = findEmployee(id);

        if (e == null) {
            System.out.println("Employee not found.");
            return;
        }

        if (e instanceof TemporaryEmployee) {
            TemporaryEmployee t = (TemporaryEmployee) e;
            System.out.print("Enter New Hours Worked: ");
            int hoursWorked = sc.nextInt();
            System.out.print("Enter New Rate Per Hour: ");
            double ratePerHour = sc.nextDouble();
            t.setHoursWorked(hoursWorked);
            t.setRatePerHour(ratePerHour);
            System.out.println("Temporary Employee salary details updated successfully.");
        } else if (e instanceof PermanentEmployee) {
            PermanentEmployee p = (PermanentEmployee) e;
            System.out.print("Enter New Basic Salary: ");
            double basicSalary = sc.nextDouble();
            System.out.print("Enter New Bonus: ");
            double bonus = sc.nextDouble();
            p.setBasicSalary(basicSalary);
            p.setBonus(bonus);
            System.out.println("Permanent Employee salary details updated successfully.");
        }
    }

    static Employee findEmployee(int id) {
        for (Employee e : employees) {
            if (e.getEmpId() == id) {
                return e;
            }
        }
        return null;
    }
}