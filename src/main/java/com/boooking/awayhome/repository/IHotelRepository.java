package com.boooking.awayhome.repository;

import com.boooking.awayhome.entity.Hotel;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IHotelRepository extends JpaRepository<Hotel, Long> {
}
