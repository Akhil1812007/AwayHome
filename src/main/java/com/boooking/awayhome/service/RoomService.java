package com.boooking.awayhome.service;

import com.boooking.awayhome.dto.RoomDto;
import com.boooking.awayhome.entity.Hotel;
import com.boooking.awayhome.entity.Room;
import com.boooking.awayhome.exception.ResourceNotFoundException;
import com.boooking.awayhome.repository.IHotelRepository;
import com.boooking.awayhome.repository.IRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService implements IRoomService{
    private ModelMapper _modelMapper;
    private final  IRoomRepository _roomsResposiory;
    private final IHotelRepository _hotelRepository;
    @Override
    public RoomDto CreateRoom(RoomDto roomDto,Long hotelId) {
            log.info("Attempting to crerate room from the input DTO");
            Hotel hotel =_hotelRepository.findById(hotelId)
                    .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with id : "+hotelId));

            Room room=_modelMapper.map(roomDto,Room.class);
            room.setHotel(hotel);
            Room createdRoom =_roomsResposiory.save(room);
            //TO DO
            //create the inventory as ther room is creates and tjhe hotel is active
            return _modelMapper.map(room,RoomDto.class);

    }

    @Override
    public List<RoomDto> GetRoomsByHotelId(Long hotelId) {

            log.info("Attempting to get room from the input DTO");
            Hotel hotel =_hotelRepository.findById(hotelId)
                    .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with id : "+hotelId));
            return hotel.getRooms()
                    .stream()
                    .map((ele)->_modelMapper.map(ele,RoomDto.class))
                    .collect(Collectors.toList());
    }

    @Override
    public RoomDto GetRoomById(Long hotelId, Long roomId) {
       log.info("Attempting to fetch the room details from the hotel");
        Hotel hotel =_hotelRepository.findById(hotelId)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with id : "+hotelId));
        return _modelMapper.map(_roomsResposiory.findById(roomId),RoomDto.class);


    }

    @Override
    public void DeleteRoomById(Long roomId) {
        log.info("Eleting the room with id : ",roomId);
        boolean isRoomExist=_roomsResposiory.existsById(roomId);
        if(!isRoomExist) throw  new ResourceNotFoundException("Room with id is not found "+roomId);
        _roomsResposiory.deleteById(roomId);

        //TO DO delelte all futture repository

    }
}
