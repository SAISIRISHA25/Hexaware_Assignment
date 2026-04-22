package com.jpa.movie.entity;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class BookingRequest {

    @NotNull(message = "Booking date is required")
    private LocalDate bookingDate;

    @Min(value = 1, message = "Seats should be at least 1")
    private int noOfSeats;

    private String bookingStatus;

    private Integer memberId;
    private Integer movieId;

    public BookingRequest() {
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }
}
