package com.boooking.awayhome.service;

import com.boooking.awayhome.dto.HotelDto;
import com.boooking.awayhome.dto.HotelInfoDto;
import com.boooking.awayhome.dto.RoomDto;

import com.boooking.awayhome.entity.Hotel;
import com.boooking.awayhome.entity.Room;
import com.boooking.awayhome.exception.ResourceNotFoundException;
import com.boooking.awayhome.repository.IHotelRepository;
import com.boooking.awayhome.repository.IRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class HotelService implements IHotelService{

    private final IHotelRepository _hotelRepository;
    private final ModelMapper _modelMapper;
    private final IInventoryService _inventoryService;
    private final IRoomRepository _roomRepository;
    @Override
    public HotelDto createHotel(HotelDto hotelDto) {
        log.info("Creating hotel with name {}",hotelDto.getName());
        Hotel hotel = _modelMapper.map(hotelDto,Hotel.class);
        hotel.setIsActive(false);
        _hotelRepository.save(hotel);
        hotelDto.setId(hotel.getId());
        log.info("Created new  hotel with name {}",hotelDto.getName());
        return hotelDto;
    }

    @Override
    public HotelDto GetHOtelById(Long id) {
        log.info("Get the hotel with id {}",id);
        Hotel hotel =_hotelRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("failed to get the hotel with id {}"+id));
        HotelDto hotelDto=_modelMapper.map(hotel, HotelDto.class);
        return hotelDto;

    }
    @Override
    public HotelDto UpdateHotel(Long id, HotelDto hotelDto){
        Hotel hotel=_hotelRepository
                .findById(id)
                .orElseThrow(()->{
                    return new ResourceNotFoundException("Failed to get hotel with id {}"+id);
                });
        log.info("hotel {}",hotel);
        _modelMapper.map(hotelDto,hotel);
        hotel.setId(id);
        hotel=_hotelRepository.save(hotel);
        return  _modelMapper.map(hotel,HotelDto.class);
    }
    @Override
    @Transactional
    public void DeleteHotel(Long id){
        Hotel hotel=_hotelRepository
                .findById(id)
                .orElseThrow(()->{
                    return new ResourceNotFoundException("Failed to get hotel with id {}"+id);
                });
        hotel.setIsActive(false);

        for(Room room:hotel.getRooms()){
            _inventoryService.deleteFutureInventories(room);
            _roomRepository.deleteById(room.getId());
        }
        _hotelRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void ActivateHotel(Long id){
        Hotel hotel=_hotelRepository
                .findById(id)
                .orElseThrow(()->{
                    return new ResourceNotFoundException("Failed to get hotel with id {}"+id);
                });
        hotel.setIsActive(true);

        for(Room room:hotel.getRooms()){
            _inventoryService.InitializeRoomsForanYear(room);
        }


    }

    @Override
    @Transactional

    public HotelInfoDto getHotelInformation(Long id){
        Hotel hotel=_hotelRepository.findById(id)
                .orElseThrow(
                        ()->{
                            return new ResourceNotFoundException("FAiled to finad hotel with id "+id);
                        }
                );
        HotelDto hotelDto =_modelMapper.map(hotel,HotelDto.class);

        List<RoomDto> rooms=hotel.getRooms()
                .stream()
                .map((ele)->_modelMapper.map(ele,RoomDto.class))
                .collect(Collectors.toList());
        return new HotelInfoDto(hotelDto,rooms);
    }


}
