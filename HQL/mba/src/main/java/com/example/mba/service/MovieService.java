package com.example.mba.service;


import java.util.List;

import com.example.mba.dao.MovieDAO;
import com.example.mba.doaimpl.MovieDAOImpl;
import com.example.mba.entity.Movie;

public class MovieService {

    MovieDAO movieDAO = new MovieDAOImpl();

    public void addMovie(Movie movie) {
        movieDAO.addMovie(movie);
    }

    public List<Movie> showAllMovies() {
        return movieDAO.showAllMovies();
    }

    public Movie searchMovieById(int movieId) {
        return movieDAO.searchMovieById(movieId);
    }

    public int updatePrice(int movieId, double price) {
        return movieDAO.updatePrice(movieId, price);
    }

    public int deleteMovie(int movieId) {
        return movieDAO.deleteMovie(movieId);
    }
}