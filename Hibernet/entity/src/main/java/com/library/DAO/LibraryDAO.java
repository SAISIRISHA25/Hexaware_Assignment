package com.library.DAO;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.library.entity.Book;
import com.library.entity.Member;

public class LibraryDAO {

    private SessionFactory factory;

    public LibraryDAO(SessionFactory factory) {
        this.factory = factory;
    }


    public void saveBook(Book book) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(book);
            tx.commit();
        }
    }

    public void deleteBook(Book book) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(book);
            tx.commit();
        }
    }

    public void updateBook(Book book) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(book);
            tx.commit();
        }
    }

    public Book findBookById(int id) {
        try (Session session = factory.openSession()) {
            return session.get(Book.class, id);
        }
    }


    public void saveMember(Member member) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(member);
            tx.commit();
        }
    }

    public Member findMemberById(int id) {
        try (Session session = factory.openSession()) {
            return session.get(Member.class, id);
        }
    }

    
}
