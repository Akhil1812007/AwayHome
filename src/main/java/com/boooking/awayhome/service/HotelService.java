package com.boooking.awayhome.service;

import com.boooking.awayhome.dto.HotelDto;
import com.boooking.awayhome.entity.Hotel;
import com.boooking.awayhome.exception.ResourceNotFoundException;
import com.boooking.awayhome.repository.IHotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class HotelService implements IHotelService{

    private final IHotelRepository _hotelRepository;
    private final ModelMapper _modelMapper;
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
    public void DeleteHotel(Long id){
        Hotel hotel=_hotelRepository
                .findById(id)
                .orElseThrow(()->{
                    return new ResourceNotFoundException("Failed to get hotel with id {}"+id);
                });
        hotel.setIsActive(false);

    }

    @Override
    public void ActivateHotel(Long id){
        Hotel hotel=_hotelRepository
                .findById(id)
                .orElseThrow(()->{
                    return new ResourceNotFoundException("Failed to get hotel with id {}"+id);
                });
        hotel.setIsActive(true);

    }

}
