package com.boooking.awayhome.controller;

import com.boooking.awayhome.advices.ApiResponse;
import com.boooking.awayhome.dto.HotelDto;
import com.boooking.awayhome.service.IHotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("admin/hotel")
public class HotelController {
    private final IHotelService _hotlService;

    @PostMapping()
    public ResponseEntity<HotelDto> createNewHotel(@RequestBody HotelDto hotelDto){
        log.info("Attemting to create a new hotel wiith name "+hotelDto.getName());
        HotelDto newHotel=_hotlService.createHotel(hotelDto);
        return new ResponseEntity<>(hotelDto, HttpStatus.CREATED);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelDto> GetHotelById(@PathVariable Long hotelId){
        log.info("Attempting to get the hotel with id : "+hotelId);
        if(hotelId!=0){
            HotelDto hotelDto=_hotlService.GetHOtelById(hotelId);
            return new ResponseEntity<>(hotelDto,HttpStatus.ACCEPTED);
        }
        return new ResponseEntity(null,HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{hotelId}")
    public ResponseEntity<HotelDto> UpdateHotel(@PathVariable Long hotelId,@RequestBody HotelDto hotelDto){
        log.info("Attempting to update the hotel with id : "+hotelId);

        HotelDto updatedHotel=_hotlService.UpdateHotel(hotelId,hotelDto);
        return new ResponseEntity<>(hotelDto,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<Void> DeleteHotel(@PathVariable Long id){
        log.info("Attempting to delete the hotel with id : "+id);
        _hotlService.DeleteHotel(id);
        return  ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<Void> ActivateHotel(@PathVariable Long id){
        log.info("Attempting to delete the hotel with id : "+id);
        _hotlService.ActivateHotel(id);
        return ResponseEntity.noContent().build();
    }
}
