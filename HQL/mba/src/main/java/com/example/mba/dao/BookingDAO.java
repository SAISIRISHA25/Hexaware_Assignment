package com.example.mba.dao;


import java.util.List;

public interface BookingDAO {
    String bookTicket(int memberId, int movieId, int tickets);
    String cancelTicket(int memberId);
    List<Object[]> showBookingDetails();
    List<Object[]> totalAmount();
}