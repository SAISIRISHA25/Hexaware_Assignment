package com.example.mba.dao;


import java.util.List;
import com.example.mba.entity.Movie;

public interface MovieDAO {
    void addMovie(Movie movie);
    List<Movie> showAllMovies();
    Movie searchMovieById(int movieId);
    int updatePrice(int movieId, double price);
    int deleteMovie(int movieId);
}