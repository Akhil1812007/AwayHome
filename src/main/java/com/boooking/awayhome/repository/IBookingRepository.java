package com.boooking.awayhome.repository;

import com.boooking.awayhome.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookingRepository extends JpaRepository<Booking,Long> {
}
