package com.library.service;
import com.library.DAO.LibraryDAO;
import com.library.entity.Book;
import com.library.entity.Member;

public class LibraryService {

    private LibraryDAO dao;

    public LibraryService(LibraryDAO dao) {
        this.dao = dao;
    }


    public void addBook(int id, String name, String author, double price) {
        Book b = new Book(id, name, author, price, "Available");
        dao.saveBook(b);
        System.out.println(" System: Book added to inventory.");
    }

    public void removeBook(int id) {
        Book b = dao.findBookById(id);
        if (b != null) {
            dao.deleteBook(b);
            System.out.println(" System: Book removed from inventory.");
        } else {
            System.out.println(" Error: Book ID not found.");
        }
    }

    public void updateBookDetails(int id, String newName, double newPrice) {
        Book b = dao.findBookById(id);
        if (b != null) {
            b.setBookName(newName);
            b.setPrice(newPrice);
            dao.updateBook(b);
            System.out.println(" System: Book details updated.");
        } else {
            System.out.println(" Error: Book not found.");
        }
    }

    public void registerMember(int id, String name, String phone) {
        Member m = new Member(id, name, phone);
        dao.saveMember(m);
        System.out.println(" System: New member registered successfully.");
    }

    public String issueBook(int bookId, int memberId) {
        Member m = dao.findMemberById(memberId);
        if (m == null) {
            return "FAILED: User is not a registered member.";
        }

        Book b = dao.findBookById(bookId);
        if (b == null) {
            return "FAILED: Book does not exist.";
        }

        if (b.getStatus().equalsIgnoreCase("Available")) {
            b.setStatus("Issued");
            dao.updateBook(b);
            return "SUCCESS: '" + b.getBookName() + "' has been issued to " + m.getMemberName();
        } else {
            return "FAILED: Book is currently issued to someone else.";
        }
    }

    public String returnBook(int bookId) {
        Book b = dao.findBookById(bookId);
        if (b != null && b.getStatus().equalsIgnoreCase("Issued")) {
            b.setStatus("Available");
            dao.updateBook(b);
            return "SUCCESS: Book returned and is now Available.";
        }
        return "FAILED: This book was not marked as Issued.";
    }
}
