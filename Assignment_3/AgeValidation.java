package Assignment_3;

import java.util.Scanner;

public class AgeValidation {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter age: ");
        if (sc.hasNextInt()) {
            int age = sc.nextInt();
            try {
                validateAge(age);
                System.out.println("Valid age entered.");
            } catch (InvalidAgeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Please enter a valid number.");
        }
        sc.close();
    }

    static void validateAge(int age) throws InvalidAgeException {
        if (age < 0 || age > 120) {
            throw new InvalidAgeException("Age must be between 0 and 120.");
        }
    }
}