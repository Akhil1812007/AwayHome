package com.boooking.awayhome.repository;

import com.boooking.awayhome.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IRoomRepository extends JpaRepository<Room, Long> {
}
