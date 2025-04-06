package com.boooking.awayhome.service;

import com.boooking.awayhome.dto.HotelDto;
import com.boooking.awayhome.dto.HotelSearchRequest;
import com.boooking.awayhome.entity.Room;
import org.springframework.data.domain.Page;

public interface IInventoryService {
    void InitializeRoomsForanYear(Room room);
    void deleteFutureInventories(Room room);
    Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
