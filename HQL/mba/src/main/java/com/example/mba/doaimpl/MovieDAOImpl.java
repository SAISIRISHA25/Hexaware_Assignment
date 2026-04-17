package com.example.mba.doaimpl;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.mba.dao.MovieDAO;
import com.example.mba.entity.Movie;
import com.example.mba.util.HibernateUtil;

public class MovieDAOImpl implements MovieDAO {

    @Override
    public void addMovie(Movie movie) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.persist(movie);

        tx.commit();
        session.close();
    }

    @Override
    public List<Movie> showAllMovies() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Movie> q = session.createQuery("from Movie", Movie.class);
        List<Movie> list = q.list();

        session.close();
        return list;
    }

    @Override
    public Movie searchMovieById(int movieId) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Movie> q = session.createQuery("from Movie where movieId=:id", Movie.class);
        q.setParameter("id", movieId);

        Movie movie = q.uniqueResult();

        session.close();
        return movie;
    }

    @Override
    public int updatePrice(int movieId, double price) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Query q = session.createQuery("update Movie set price=:pr where movieId=:id");
        q.setParameter("pr", price);
        q.setParameter("id", movieId);

        int count = q.executeUpdate();

        tx.commit();
        session.close();
        return count;
    }

    @Override
    public int deleteMovie(int movieId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Query q = session.createQuery("delete from Movie where movieId=:id");
        q.setParameter("id", movieId);

        int count = q.executeUpdate();

        tx.commit();
        session.close();
        return count;
    }
}