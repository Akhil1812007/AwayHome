package com.boooking.awayhome.repository;

import com.boooking.awayhome.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGuestRepository extends JpaRepository<Guest,Long> {
}
