package com.boooking.awayhome.service;

import com.boooking.awayhome.dto.BookingDto;
import com.boooking.awayhome.dto.BookingRequest;
import com.boooking.awayhome.dto.GuestDto;

import java.util.List;

public interface IBookingService {
    public BookingDto initialzeBooking(BookingRequest bookingRequest) ;

    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtos);
}
