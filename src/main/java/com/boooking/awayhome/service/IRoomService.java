package com.boooking.awayhome.service;

import com.boooking.awayhome.dto.RoomDto;

import java.util.List;

public interface IRoomService {
    RoomDto CreateRoom(RoomDto roomDto,Long hotelId);

    List<RoomDto> GetRoomsByHotelId(Long HotelId);
    RoomDto GetRoomById(Long hotelId,Long roomId);
    void DeleteRoomById(Long roomId);

}
