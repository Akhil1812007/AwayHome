package com.boooking.awayhome.service;

import com.boooking.awayhome.dto.RoomDto;

import java.util.List;

public interface IRoomService {
    RoomDto CreateRoom(RoomDto roomDto);

    List<RoomDto> GetRoomsByHotelId(Long HotelId);
}
