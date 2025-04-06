package com.boooking.awayhome.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {
    private Long HotelId;
    private Long RoomId;
    private LocalDate checkInDate;
    private LocalDate checkoutDate;
    private Integer roomsCount;
}
