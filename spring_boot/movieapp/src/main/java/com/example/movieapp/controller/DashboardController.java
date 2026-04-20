package com.example.movieapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DashboardController {

    @Autowired
    private BookController bookController;

    @Autowired
    private MovieController movieController;

    @GetMapping("/dashboard")
    public Map<String, Integer> getDashboard() {
        Map<String, Integer> data = new HashMap<>();
        data.put("totalBooks", bookController.getBookCount());
        data.put("totalMovies", movieController.getMovieCount());
        return data;
    }
}
