package EmployeeManagement;

public class TemporaryEmployee extends Employee {
    private int hoursWorked;
    private double ratePerHour;

    public TemporaryEmployee(int empId, String name, int hoursWorked, double ratePerHour) {
        super(empId, name);
        this.hoursWorked = hoursWorked;
        this.ratePerHour = ratePerHour;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public double getRatePerHour() {
        return ratePerHour;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public void setRatePerHour(double ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    public double calculateSalary() {
        return hoursWorked * ratePerHour;
    }

    public void displayDetails() {
        System.out.println("Employee Type : Temporary Employee");
        System.out.println("Employee ID   : " + empId);
        System.out.println("Name          : " + name);
        System.out.println("Hours Worked  : " + hoursWorked);
        System.out.println("Rate Per Hour : " + ratePerHour);
        System.out.println("Salary        : " + calculateSalary());
        System.out.println("-----------------------------------");
    }
}