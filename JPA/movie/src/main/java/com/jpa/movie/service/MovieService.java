package com.jpa.movie.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpa.movie.entity.Movie;
import com.jpa.movie.repository.MovieRepository;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Integer id) {
        return movieRepository.findById(id).orElse(null);
    }

    public Movie updateMovie(Integer id, Movie movie) {
        Movie existing = movieRepository.findById(id).orElse(null);

        if (existing != null) {
            existing.setMovieName(movie.getMovieName());
            existing.setLanguage(movie.getLanguage());
            existing.setGenre(movie.getGenre());
            existing.setDuration(movie.getDuration());
            existing.setTicketPrice(movie.getTicketPrice());
            return movieRepository.save(existing);
        }
        return null;
    }

    public String deleteMovie(Integer id) {
        Movie existing = movieRepository.findById(id).orElse(null);
        if (existing != null) {
            movieRepository.deleteById(id);
            return "Movie deleted successfully";
        }
        return "Movie not found";
    }

    public List<Movie> searchByLanguage(String language) {
        return movieRepository.findByLanguage(language);
    }

    public List<Movie> searchByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    public String updateTicketPrice(Integer id, double price) {
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie == null) {
            return "Movie not found";
        }
        movie.setTicketPrice(price);
        movieRepository.save(movie);
        return "Ticket price updated successfully";
    }
}
