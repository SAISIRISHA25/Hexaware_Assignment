package com.jpa.movie.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookingId;

    @NotNull(message = "Booking date is required")
    private LocalDate bookingDate;

    @Min(value = 1, message = "At least 1 seat must be booked")
    private int noOfSeats;

    private double totalAmount;

    @NotBlank(message = "Booking status cannot be empty")
    private String bookingStatus;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public Booking() {
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
