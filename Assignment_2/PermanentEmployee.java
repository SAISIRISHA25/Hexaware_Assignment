package EmployeeManagement;

public class PermanentEmployee extends Employee {
    private double basicSalary;
    private double bonus;

    public PermanentEmployee(int empId, String name, double basicSalary, double bonus) {
        super(empId, name);
        this.basicSalary = basicSalary;
        this.bonus = bonus;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double calculateSalary() {
        return basicSalary + bonus;
    }

    public void displayDetails() {
        System.out.println("Employee Type : Permanent Employee");
        System.out.println("Employee ID   : " + empId);
        System.out.println("Name          : " + name);
        System.out.println("Basic Salary  : " + basicSalary);
        System.out.println("Bonus         : " + bonus);
        System.out.println("Salary        : " + calculateSalary());
        System.out.println("-----------------------------------");
    }
}