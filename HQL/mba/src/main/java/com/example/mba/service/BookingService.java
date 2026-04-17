package com.example.mba.service;


import java.util.List;

import com.example.mba.dao.BookingDAO;
import com.example.mba.doaimpl.BookingDAOImpl;

public class BookingService {

    BookingDAO bookingDAO = new BookingDAOImpl();

    public String bookTicket(int memberId, int movieId, int tickets) {
        return bookingDAO.bookTicket(memberId, movieId, tickets);
    }

    public String cancelTicket(int memberId) {
        return bookingDAO.cancelTicket(memberId);
    }

    public List<Object[]> showBookingDetails() {
        return bookingDAO.showBookingDetails();
    }

    public List<Object[]> totalAmount() {
        return bookingDAO.totalAmount();
    }
}
