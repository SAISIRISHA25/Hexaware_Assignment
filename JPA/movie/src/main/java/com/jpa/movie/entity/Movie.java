package com.jpa.movie.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    @NotBlank(message = "Movie name cannot be empty")
    private String movieName;

    @NotBlank(message = "Language cannot be empty")
    private String language;

    @NotBlank(message = "Genre cannot be empty")
    private String genre;

    @NotBlank(message = "Duration cannot be empty")
    private String duration;

    @Min(value = 1, message = "Ticket price must be greater than 0")
    private double ticketPrice;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Booking> bookings;

    public Movie() {
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
