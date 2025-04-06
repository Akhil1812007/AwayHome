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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService implements IRoomService{
    private final ModelMapper _modelMapper;
    private final  IRoomRepository _roomsResposiory;
    private final IHotelRepository _hotelRepository;
    private final IInventoryService _inventoryService;
    @Override
    public RoomDto CreateRoom(RoomDto roomDto,Long hotelId) {
            log.info("Attempting to crerate room from the input DTO");
            Hotel hotel =_hotelRepository.findById(hotelId)
                    .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with id : "+hotelId));

            Room room=_modelMapper.map(roomDto,Room.class);
            room.setHotel(hotel);
            Room createdRoom =_roomsResposiory.save(room);
            if(hotel.getIsActive())
                _inventoryService.InitializeRoomsForanYear(room);

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
        Room room =_roomsResposiory.findById(roomId)
                .orElseThrow(()-> new ResourceNotFoundException("room not found with id : "+roomId));
        return _modelMapper.map(_roomsResposiory.findById(roomId),RoomDto.class);


    }

    @Override
    @Transactional
    public void DeleteRoomById(Long roomId) {
        log.info("Deleting the room with id : ",roomId);
        boolean isRoomExist=_roomsResposiory.existsById(roomId);
        if(!isRoomExist) throw  new ResourceNotFoundException("Room with id is not found "+roomId);
        Room room =_roomsResposiory.findById(roomId)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with id : "+roomId));
        _inventoryService.deleteFutureInventories(room);
        _roomsResposiory.deleteById(roomId);




    }
}
