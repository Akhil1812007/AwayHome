package com.boooking.awayhome.controller;

import com.boooking.awayhome.dto.HotelDto;
import com.boooking.awayhome.dto.HotelInfoDto;
import com.boooking.awayhome.dto.HotelSearchRequest;
import com.boooking.awayhome.service.IHotelService;
import com.boooking.awayhome.service.IInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hotels")
public class HotelBrowseController {
    private final IInventoryService _inventoryService;
    private final IHotelService _hotelService;
    @GetMapping("/search")
    public ResponseEntity<Page<HotelDto>> searchHptel(@RequestBody HotelSearchRequest hotelSearchRequest){
        Page<HotelDto> page=_inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(page);

    }
    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelInfoDto> getHotelInformation(@PathVariable Long hotelId){
        HotelInfoDto hotelInfomation=_hotelService.getHotelInformation(hotelId);
        return ResponseEntity.ok(hotelInfomation);
    }
}
