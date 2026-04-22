package com.jpa.movie.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpa.movie.entity.Booking;
import com.jpa.movie.entity.BookingRequest;
import com.jpa.movie.entity.Member;
import com.jpa.movie.entity.Movie;
import com.jpa.movie.repository.BookingRepository;
import com.jpa.movie.repository.MemberRepository;
import com.jpa.movie.repository.MovieRepository;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MovieRepository movieRepository;

    public Booking addBooking(BookingRequest request) {
        Member member = memberRepository.findById(request.getMemberId()).orElse(null);
        Movie movie = movieRepository.findById(request.getMovieId()).orElse(null);

        if (member == null || movie == null) {
            return null;
        }

        Booking booking = new Booking();
        booking.setBookingDate(request.getBookingDate());
        booking.setNoOfSeats(request.getNoOfSeats());
        booking.setBookingStatus(request.getBookingStatus());
        booking.setMember(member);
        booking.setMovie(movie);

        double total = request.getNoOfSeats() * movie.getTicketPrice();

        if (request.getNoOfSeats() >= 5) {
            total = total - (total * 0.10); 
        }

        booking.setTotalAmount(total);

        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Integer id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public String cancelBooking(Integer id) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking == null) {
            return "Booking not found";
        }

        booking.setBookingStatus("Cancelled");
        bookingRepository.save(booking);
        return "Booking cancelled successfully";
    }

    public List<Booking> getBookingsByMemberId(Integer memberId) {
        return bookingRepository.findByMemberMemberId(memberId);
    }

    public String displayConfirmation(Integer id) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking == null) {
            return "Booking not found";
        }

        return "Booking Confirmed | Booking ID: " + booking.getBookingId()
                + " | Member: " + booking.getMember().getName()
                + " | Movie: " + booking.getMovie().getMovieName()
                + " | Seats: " + booking.getNoOfSeats()
                + " | Total Amount: " + booking.getTotalAmount()
                + " | Status: " + booking.getBookingStatus();
    }
}