package com.boooking.awayhome.service;

import com.boooking.awayhome.dto.BookingDto;
import com.boooking.awayhome.dto.BookingRequest;
import com.boooking.awayhome.dto.GuestDto;
import com.boooking.awayhome.entity.*;
import com.boooking.awayhome.entity.enums.BookingStatus;
import com.boooking.awayhome.exception.ResourceNotFoundException;
import com.boooking.awayhome.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.rmi.server.LogStream.log;

@Service
@Slf4j
@RequiredArgsConstructor

public class BookingService implements  IBookingService{
    private final IBookingRepository _bookingRepository;
    private final IHotelRepository _hotelRepository;
    private final IRoomRepository _roomRepository;
    private final IInventoryRepository _inventoryRepository;
    private final ModelMapper _mapperMapper;
    private final IGuestRepository _guestRepository;

    @Transactional

    public BookingDto initialzeBooking(BookingRequest bookingRequest) {
        BookingService.log.info("Initializing booking for Hotel {");
        Hotel hotel =_hotelRepository.findById(bookingRequest.getHotelId())
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with id "+ bookingRequest.getHotelId()));
        Room room=_roomRepository.findById(bookingRequest.getRoomId())
                .orElseThrow(()->new ResourceNotFoundException("Room with id "+ bookingRequest.getRoomId()));
        List<Inventory> inventoryList =_inventoryRepository.findAndLockAllMatchedInventory(bookingRequest.getRoomId(),bookingRequest.getCheckInDate(),bookingRequest.getCheckoutDate(),bookingRequest.getRoomsCount());
        long roomsCount= ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(),bookingRequest.getCheckoutDate())+1;
        if(inventoryList.size()!=roomsCount){
            throw new IllegalStateException("Room is not avaialble");
        }
        //block for the 10 min
        for(Inventory inventory:inventoryList){
            inventory.setReservedCount(inventory.getReservedCount()+bookingRequest.getRoomsCount());

        }
        _inventoryRepository.saveAll(inventoryList);
        User user=new User();
        user.setId(1L);//TO DO REMOVE DUMMY USER

        //TO DO :CALCULATE DYNAMIC
        Booking booking =Booking.builder()
                .bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequest.getCheckInDate())
                .checkOutDate(bookingRequest.getCheckoutDate())
                .user(user)
                .roomsCount(bookingRequest.getRoomsCount())
                .amount(BigDecimal.TEN)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
         booking =_bookingRepository.save(booking);
         return _mapperMapper.map(booking, BookingDto.class);





    }

    @Override
    @Transactional
    public BookingDto addGuests(Long bookingId, List<GuestDto> guestDtos) {
        log("Adding guest for booking id "+ bookingId);
        Booking booking =_bookingRepository.findById(bookingId)
                .orElseThrow(()->{
                   return  new ResourceNotFoundException("Booking not found with id "+bookingId);
                });
        if(hasBookingExpired(booking)){
            throw new IllegalStateException("Booking has already expired");
        }
        if(booking.getBookingStatus()!=BookingStatus.RESERVED){
            throw new IllegalStateException("not reserved");

        }
        for(GuestDto guestDto:guestDtos){
            Guest user =_mapperMapper.map(guestDto, Guest.class);
            user.setUser(getCurrentUser());
            user=_guestRepository.save(user);
            booking.getGuests().add(user);
        }
        booking.setBookingStatus(BookingStatus.GUEST_ADDED);
        booking=_bookingRepository.save(booking);


        return _mapperMapper.map(booking, BookingDto.class);
    }

    private User getCurrentUser() {
        User user=new User();
        user.setId(1L);//TO DO REMOVE DUMMY USER
        return user;
    }

    private boolean hasBookingExpired(Booking booking){
       return  booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }
}
