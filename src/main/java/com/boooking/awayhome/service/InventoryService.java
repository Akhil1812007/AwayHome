package com.boooking.awayhome.service;

import com.boooking.awayhome.dto.HotelDto;
import com.boooking.awayhome.dto.HotelSearchRequest;
import com.boooking.awayhome.entity.Hotel;
import com.boooking.awayhome.entity.Inventory;
import com.boooking.awayhome.entity.Room;
import com.boooking.awayhome.repository.IInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService implements  IInventoryService {
    private final IInventoryRepository _inventoryRepository;
    private final ModelMapper _modelMapper;

    @Override
    public void InitializeRoomsForanYear(Room room) {
        LocalDate today=LocalDate.now();
        LocalDate endDate=today.plusYears(1);
        for(;!today.isAfter(endDate);today=today.plusDays(1)){
            Inventory inventory=Inventory.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .bookedCount(0)
                    .reservedCount(0)
                    .date(today)
                    .price(room.getBasePrice())
                    .city(room.getHotel().getCity())
                    .totalCount(room.getTotalCount())
                    .isClosed(false)
                    .surgeFactor(BigDecimal.ONE)
                    .build();
            _inventoryRepository.save(inventory);

        }

    }

    @Override
    @Transactional
    public void deleteFutureInventories(Room room) {
        LocalDate today=LocalDate.now();
        _inventoryRepository.deleteByRoom(room);

    }

    @Override
    public Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest) {
        Pageable pageble= PageRequest.of(hotelSearchRequest.getPage(),hotelSearchRequest.getSize());
        Long datacount= ChronoUnit.DAYS.between(hotelSearchRequest.getStartDate(),hotelSearchRequest.getEndDate())+1;
        Page<Hotel> hotelPage=_inventoryRepository.getHotelsWithAvailableInventory(hotelSearchRequest.getCity(),hotelSearchRequest.getStartDate(),hotelSearchRequest.getEndDate(),

                hotelSearchRequest.getRoomsCount(),datacount ,pageble);

        return hotelPage.map(
                (ele)->{
                   return  _modelMapper.map(ele, HotelDto.class);
                }
        );

    }
}
