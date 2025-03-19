package com.boooking.awayhome.controller;

import com.boooking.awayhome.dto.RoomDto;
import com.boooking.awayhome.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/hotel/{hotelId}/room")
@RequiredArgsConstructor
public class RoomAdminController {
    private final IRoomService _roomService;
    @PostMapping
    public ResponseEntity<RoomDto> createRoom(@PathVariable Long hotelId, @RequestBody RoomDto roomDto){
        RoomDto room=_roomService.CreateRoom(roomDto,hotelId);
        return new ResponseEntity<>(room, HttpStatus.CREATED);

    }
    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRooms(@PathVariable Long hotelId){
        return ResponseEntity.ok( _roomService.GetRoomsByHotelId(hotelId));
    }
    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long roomId,@PathVariable Long hotelId){
        return ResponseEntity.ok( _roomService.GetRoomById(hotelId,roomId));
    }

}
