package com.example.mba.doaimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.mba.dao.BookingDAO;
import com.example.mba.entity.Member;
import com.example.mba.entity.Movie;
import com.example.mba.util.HibernateUtil;



public class BookingDAOImpl implements BookingDAO {

    @Override
    public String bookTicket(int memberId, int movieId, int tickets) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        try {
            Query<Member> mq = session.createQuery("from Member where memberId=:id", Member.class);
            mq.setParameter("id", memberId);
            Member member = mq.uniqueResult();

            if (member == null) {
                session.close();
                return "Member not found";
            }

            Query<Movie> movq = session.createQuery("from Movie where movieId=:id", Movie.class);
            movq.setParameter("id", movieId);
            Movie movie = movq.uniqueResult();

            if (movie == null) {
                session.close();
                return "Movie not found";
            }

            if (movie.getSeats() < tickets) {
                session.close();
                return "Not enough seats available";
            }

            member.setMovieid(movieId);
            member.setTickets(tickets);
            movie.setSeats(movie.getSeats() - tickets);

            session.merge(member);
            session.merge(movie);

            tx.commit();
            session.close();
            return "Ticket booked successfully";

        } catch (Exception e) {
            tx.rollback();
            session.close();
            return "Booking failed";
        }
    }

    @Override
    public String cancelTicket(int memberId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        try {
            Query<Member> mq = session.createQuery("from Member where memberId=:id", Member.class);
            mq.setParameter("id", memberId);
            Member member = mq.uniqueResult();

            if (member == null) {
                session.close();
                return "Member not found";
            }

            if (member.getMovieid() == 0 || member.getTickets() == 0) {
                session.close();
                return "No booking found";
            }

            Query<Movie> movq = session.createQuery("from Movie where movieId=:id", Movie.class);
            movq.setParameter("id", member.getMovieid());
            Movie movie = movq.uniqueResult();

            if (movie != null) {
                movie.setSeats(movie.getSeats() + member.getTickets());
                session.merge(movie);
            }

            member.setMovieid(0);
            member.setTickets(0);
            session.merge(member);

            tx.commit();
            session.close();
            return "Ticket cancelled successfully";

        } catch (Exception e) {
            tx.rollback();
            session.close();
            return "Cancellation failed";
        }
    }

    @Override
    public List<Object[]> showBookingDetails() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Object[]> q = session.createQuery(
                "select m.memberId, m.memberName, mv.movieName, m.tickets, mv.price " +
                "from Member m, Movie mv where m.movieId = mv.movieId",
                Object[].class);

        List<Object[]> list = q.list();
        session.close();
        return list;
    }

    @Override
    public List<Object[]> totalAmount() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Object[]> q = session.createQuery(
                "select m.memberId, m.memberName, mv.movieName, m.tickets, (m.tickets * mv.price) " +
                "from Member m, Movie mv where m.movieId = mv.movieId",
                Object[].class);

        List<Object[]> list = q.list();
        session.close();
        return list;
    }
}
