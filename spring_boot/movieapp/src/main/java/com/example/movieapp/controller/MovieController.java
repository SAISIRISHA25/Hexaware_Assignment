package com.example.movieapp.controller;


import com.example.movieapp.model.Movie;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private List<Movie> movies = new ArrayList<>();

    public MovieController() {
        movies.add(new Movie(101, "KGF", "Yash", 9.5));
        movies.add(new Movie(102, "Pushpa", "Allu Arjun", 8.9));
    }

    @PostMapping("/add")
    public String addMovie(@RequestBody Movie movie) {
        for (Movie m : movies) {
            if (m.getMovieId() == movie.getMovieId()) {
                return "Movie ID already exists";
            }
        }
        movies.add(movie);
        return "Movie added successfully";
    }

    @GetMapping("/all")
    public List<Movie> getAllMovies() {
        return movies;
    }

    @GetMapping("/{id}")
    public Object getMovieById(@PathVariable int id) {
        for (Movie m : movies) {
            if (m.getMovieId() == id) {
                return m;
            }
        }
        return "Movie not found";
    }

    @PutMapping("/update/{id}")
    public String updateMovie(@PathVariable int id, @RequestBody Movie updatedMovie) {
        for (Movie m : movies) {
            if (m.getMovieId() == id) {
                m.setMovieName(updatedMovie.getMovieName());
                m.setHero(updatedMovie.getHero());
                m.setRating(updatedMovie.getRating());
                return "Movie updated successfully";
            }
        }
        return "Movie not found";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMovie(@PathVariable int id) {
        Iterator<Movie> iterator = movies.iterator();
        while (iterator.hasNext()) {
            Movie m = iterator.next();
            if (m.getMovieId() == id) {
                iterator.remove();
                return "Movie deleted successfully";
            }
        }
        return "Movie not found";
    }

    @GetMapping("/top")
    public Object getTopRatedMovie() {
        if (movies.isEmpty()) {
            return "No movies available";
        }

        Movie topMovie = movies.get(0);
        for (Movie m : movies) {
            if (m.getRating() > topMovie.getRating()) {
                topMovie = m;
            }
        }
        return topMovie;
    }

    @GetMapping("/hero/{heroName}")
    public List<Movie> getMovieByHero(@PathVariable String heroName) {
        List<Movie> result = new ArrayList<>();
        for (Movie m : movies) {
            if (m.getHero().equalsIgnoreCase(heroName)) {
                result.add(m);
            }
        }
        return result;
    }

    public int getMovieCount() {
        return movies.size();
    }
}
