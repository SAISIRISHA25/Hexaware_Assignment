package com.example.movieapp.model;


public class Movie {
    private int movieId;
    private String movieName;
    private String hero;
    private double rating;

    @Override
    public String toString() {
        return "Movie [movieId=" + movieId + ", movieName=" + movieName + ", hero=" + hero + ", rating=" + rating + "]";
    }

    public Movie() {
    }

    public Movie(int movieId, String movieName, String hero, double rating) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.hero = hero;
        this.rating = rating;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getHero() {
        return hero;
    }

    public void setHero(String hero) {
        this.hero = hero;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
