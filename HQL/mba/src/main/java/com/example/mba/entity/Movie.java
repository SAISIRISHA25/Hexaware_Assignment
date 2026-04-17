package com.example.mba.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Movie {
    @Id
    private int movieid;
    private String moviename;
    private double price;
    private int seats;
    public Movie() {
    }
    public Movie(int movieid, String moviename, double price, int seats) {
        this.movieid = movieid;
        this.moviename = moviename;
        this.price = price;
        this.seats = seats;
    }
    public int getMovieid() {
        return movieid;
    }
    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }
    public String getMoviename() {
        return moviename;
    }
    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getSeats() {
        return seats;
    }
    public void setSeats(int seats) {
        this.seats = seats;
    }
    @Override
    public String toString() {
        return "Movie [movieid=" + movieid + ", moviename=" + moviename + ", price=" + price + ", seats=" + seats + "]";
    }
    
    


}
