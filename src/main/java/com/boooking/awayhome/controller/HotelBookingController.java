package com.boooking.awayhome.controller;

import com.boooking.awayhome.dto.BookingDto;
import com.boooking.awayhome.dto.BookingRequest;
import com.boooking.awayhome.dto.GuestDto;
import com.boooking.awayhome.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/bookings")
@RequiredArgsConstructor
public class HotelBookingController {
    private final BookingService _bookingService;

   // private
    @PostMapping("/initializeBooking")
    public ResponseEntity<BookingDto> initializeBooking(@RequestBody BookingRequest bookingRequest){
        return ResponseEntity.ok(_bookingService.initialzeBooking(bookingRequest));
    }

    @PostMapping("/{bookingId}/addGuest")
    public ResponseEntity<BookingDto> addGuests(@PathVariable Long bookingId, @RequestBody List<GuestDto> guestDtos){
        return ResponseEntity.ok(_bookingService.addGuests(bookingId,guestDtos));
    }
}
