package com.boooking.awayhome.service;

import com.boooking.awayhome.dto.RoomDto;
import com.boooking.awayhome.entity.Hotel;
import com.boooking.awayhome.entity.Room;
import com.boooking.awayhome.repository.IRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService implements IRoomService{
    private ModelMapper _modelMapper;
    private IRoomRepository _roomsResposiory;
//
//     log.info("Creating hotel with name {}",hotelDto.getName());
//    Hotel hotel = _modelMapper.map(hotelDto,Hotel.class);
//        hotel.setIsActive(false);
//        _hotelRepository.save(hotel);
//        hotelDto.setId(hotel.getId());
//        log.info("Created new  hotel with name {}",hotelDto.getName());
//        return hotelDto;
    @Override
    public RoomDto CreateRoom(RoomDto roomDto) {
            log.info("Attempting to crerate room from the input DTO");
            Room room=_modelMapper.map(roomDto,Room.class);
            Room createdRoom =_roomsResposiory.save(room);
            return _modelMapper.map(room,RoomDto.class);
    }

    @Override
    public List<RoomDto> GetRoomsByHotelId(Long HotelId) {
        return List.of();
    }
}
