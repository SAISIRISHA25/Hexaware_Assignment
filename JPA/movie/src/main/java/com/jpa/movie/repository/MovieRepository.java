package com.jpa.movie.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.jpa.movie.entity.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findByLanguage(String language);
    List<Movie> findByGenre(String genre);
}