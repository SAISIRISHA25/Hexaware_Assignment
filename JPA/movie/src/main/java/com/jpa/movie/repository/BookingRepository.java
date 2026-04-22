package com.jpa.movie.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.jpa.movie.entity.Booking;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByMemberMemberId(Integer memberId);
}