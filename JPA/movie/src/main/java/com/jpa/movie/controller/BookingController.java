package com.jpa.movie.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jpa.movie.entity.Booking;
import com.jpa.movie.entity.BookingRequest;
import com.jpa.movie.service.BookingService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/add")
    public Booking addBooking(@Valid @RequestBody BookingRequest request) {
        return bookingService.addBooking(request);
    }

    @GetMapping("/all")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Integer id) {
        return bookingService.getBookingById(id);
    }

    @PutMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable Integer id) {
        return bookingService.cancelBooking(id);
    }

    @GetMapping("/member/{memberId}")
    public List<Booking> getBookingsByMemberId(@PathVariable Integer memberId) {
        return bookingService.getBookingsByMemberId(memberId);
    }

    @GetMapping("/confirm/{id}")
    public String displayConfirmation(@PathVariable Integer id) {
        return bookingService.displayConfirmation(id);
    }
}
