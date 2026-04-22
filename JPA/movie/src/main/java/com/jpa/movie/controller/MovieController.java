package com.jpa.movie.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jpa.movie.entity.Movie;
import com.jpa.movie.service.MovieService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("/add")
    public Movie addMovie(@Valid @RequestBody Movie movie) {
        return movieService.addMovie(movie);
    }

    @GetMapping("/all")
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Integer id) {
        return movieService.getMovieById(id);
    }

    @PutMapping("/update/{id}")
    public Movie updateMovie(@PathVariable Integer id, @Valid @RequestBody Movie movie) {
        return movieService.updateMovie(id, movie);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Integer id) {
        return movieService.deleteMovie(id);
    }

    @GetMapping("/language/{language}")
    public List<Movie> searchByLanguage(@PathVariable String language) {
        return movieService.searchByLanguage(language);
    }

    @GetMapping("/genre/{genre}")
    public List<Movie> searchByGenre(@PathVariable String genre) {
        return movieService.searchByGenre(genre);
    }

    @PutMapping("/update-price/{id}/{price}")
    public String updateTicketPrice(@PathVariable Integer id, @PathVariable double price) {
        return movieService.updateTicketPrice(id, price);
    }
}
