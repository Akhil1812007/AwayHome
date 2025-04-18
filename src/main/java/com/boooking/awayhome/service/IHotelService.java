package com.boooking.awayhome.service;

import com.boooking.awayhome.dto.HotelDto;
import com.boooking.awayhome.dto.HotelInfoDto;

public interface IHotelService {
    HotelDto createHotel (HotelDto hotelDto);
    HotelDto GetHOtelById(Long id);
    HotelDto UpdateHotel(Long id,HotelDto hotelDto);

    void DeleteHotel(Long id);

    void ActivateHotel(Long id);

    HotelInfoDto getHotelInformation(Long hotelId);
}
