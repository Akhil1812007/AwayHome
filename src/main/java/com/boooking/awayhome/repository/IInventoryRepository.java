package com.boooking.awayhome.repository;

import com.boooking.awayhome.entity.Hotel;
import com.boooking.awayhome.entity.Inventory;
import com.boooking.awayhome.entity.Room;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IInventoryRepository  extends JpaRepository<Inventory,Long> {
    void deleteByRoom(Room room);

    @Query("""
            SELECT DISTINCT i.hotel
            FROM Inventory i
            WHERE i.city= :city 
                AND i.date BETWEEN :startDate AND :endDate
                AND i.isClosed=false
                AND (i.totalCount-i.bookedCount-i.reservedCount)>=:roomsCount
            GROUP BY i.hotel, i.room
            HAVING COUNT(i.date)=:dateCount
            """)
    Page<Hotel> getHotelsWithAvailableInventory(
            @Param("city") String city,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomsCount") Integer roomsCount,
            @Param("dateCount") Long dateCount,
            Pageable pageable
          );
    @Query(
            """ 
            SELECT i
            FROM Inventory i
            WHERE i.room.id= :roomId 
                AND i.date BETWEEN :startDate AND :endDate
                AND i.isClosed=false
                AND (i.totalCount-i.bookedCount-i.reservedCount)>=:roomsCount
                    """
    )

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Inventory> findAndLockAllMatchedInventory(
            @Param("roomId") Long roomId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomsCount") Integer roomsCount


    );





}
