package com.boooking.awayhome.dto;

import com.boooking.awayhome.entity.Guest;
import com.boooking.awayhome.entity.Hotel;
import com.boooking.awayhome.entity.Room;
import com.boooking.awayhome.entity.User;
import com.boooking.awayhome.entity.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
@Getter
@Setter
public class BookingDto {

    private Long id;
    private BookingStatus bookingStatus;
    private Integer roomsCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDate checkInDate;
    private LocalDate CheckOutDate;
    private Set<GuestDto> guests;
    private BigDecimal amount;
}
