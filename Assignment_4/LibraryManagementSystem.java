package Assignment_5;

import java.util.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Book> books = new ArrayList<>();
        List<Member> members = new ArrayList<>();

        while (true) {
            System.out.println("\n--- LIBRARY MANAGEMENT SYSTEM ---");
            System.out.println("1. Add Book          2. Create Member");
            System.out.println("3. Issue Book        4. Return Book");
            System.out.println("5. Show All Books    6. Update Book");
            System.out.println("7. Remove Book       8. Remove Member");
            System.out.println("9. Exit");
            System.out.print("Select an option: ");
            
            int choice = sc.nextInt();
            sc.nextLine(); 

            if (choice == 1) { 
                System.out.print("Enter Book ID: ");
                int id = sc.nextInt(); sc.nextLine();
                System.out.print("Enter Full Title: ");
                String title = sc.nextLine();
                System.out.print("Enter Author Name: ");
                String author = sc.nextLine();
                books.add(new Book(id, title, author));
                System.out.println("Success: Book added to inventory.");

            } else if (choice == 2) { 
                System.out.print("Enter Member ID: ");
                int id = sc.nextInt(); sc.nextLine();
                System.out.print("Enter Member Full Name: ");
                String name = sc.nextLine();
                members.add(new Member(id, name));
                System.out.println("Success: Member registered.");

            } else if (choice == 3) { 
                System.out.print("Enter Member ID: ");
                int mid = sc.nextInt();
                System.out.print("Enter Book ID: ");
                int bid = sc.nextInt();

                Member m = null; for(Member x : members) if(x.memberId == mid) m = x;
                Book b = null; for(Book x : books) if(x.bookId == bid) b = x;

                
                if (m != null && b != null && b.isAvailable && m.issuedBook == null) {
                    m.issuedBook = b;
                    b.isAvailable = false;
                    m.issueDate = LocalDate.now();
                    m.dueDate = m.issueDate.plusDays(7);
                    System.out.println("Issued: '" + b.title + "' to " + m.name);
                    System.out.println("Due Date: " + m.dueDate);
                } else {
                    System.out.println("Error: Check if IDs are correct, book is available, or member already has a book.");
                }

            } else if (choice == 4) { 
                System.out.print("Enter Member ID returning the book: ");
                int mid = sc.nextInt();
                for (Member m : members) {
                    if (m.memberId == mid && m.issuedBook != null) {
                        LocalDate today = LocalDate.now();
                        long lateDays = ChronoUnit.DAYS.between(m.dueDate, today);
                        
                        if (lateDays > 0) {
                            System.out.println("LATE RETURN! Fine: ₹" + (lateDays * 10));
                        } else {
                            System.out.println("Returned on time. No fine.");
                        }
                        m.issuedBook.isAvailable = true;
                        m.issuedBook = null;
                        break;
                    }
                }

            } else if (choice == 5) { 
                System.out.println("\nID\tTITLE\t\t\tAUTHOR\t\tSTATUS");
                for (Book b : books) {
                    System.out.println(b.bookId + "\t" + b.title + "\t\t" + b.author + "\t\t" + (b.isAvailable ? "Available" : "Issued"));
                }

            } else if (choice == 6) { 
                System.out.print("Enter Book ID to Update: ");
                int bid = sc.nextInt(); sc.nextLine();
                for (Book b : books) {
                    if (b.bookId == bid) {
                        System.out.print("Enter New Title: "); b.title = sc.nextLine();
                        System.out.print("Enter New Author: "); b.author = sc.nextLine();
                        System.out.println("Book details updated.");
                    }
                }

            } else if (choice == 7) {
                System.out.print("Enter Book ID to Remove: ");
                int bid = sc.nextInt();
                books.removeIf(b -> b.bookId == bid);
                System.out.println("Book removed from system.");

            } else if (choice == 8) { 
                System.out.print("Enter Member ID to Remove: ");
                int mid = sc.nextInt();
                members.removeIf(m -> m.memberId == mid);
                System.out.println("Member removed from system.");

            } else if (choice == 9) {
                System.out.println("Exiting System...");
                break;
            }
        }
        sc.close();
    }
}
