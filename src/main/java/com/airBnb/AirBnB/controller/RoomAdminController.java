package com.airBnb.AirBnB.controller;

import com.airBnb.AirBnB.dto.RoomDto;
import com.airBnb.AirBnB.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/admin/hotels/{hotelId}/rooms")
@RequiredArgsConstructor
public class RoomAdminController {

    private final RoomService roomService;

    @PostMapping
    @Operation(summary = "Create a new room in a hotel", tags = {"Room Management"})
    public ResponseEntity<RoomDto> createNewRoom(@PathVariable Long hotelId, @RequestBody RoomDto roomDto) {
        RoomDto room = roomService.createNewRoom(hotelId, roomDto);
        return new ResponseEntity<>(room, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all rooms in a hotel", tags = {"Room Management"})
    public ResponseEntity<List<RoomDto>> getAllRoomsInHotel(@PathVariable Long hotelId) {
        return ResponseEntity.ok(roomService.getAllRoomsInAHotel(hotelId));
    }

    @GetMapping("/{roomId}")
    @Operation(summary = "Get room details by ID", tags = {"Room Management"})
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long roomId) {
        return ResponseEntity.ok(roomService.getRoomById(roomId));
    }

    @DeleteMapping("/{roomId}")
    @Operation(summary = "Delete a room", tags = {"Room Management"})
    public ResponseEntity<Void> deleteRoomById(@PathVariable Long roomId) {
        roomService.deleteRoomById(roomId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{roomId}")
    @Operation(summary = "Update room details", tags = {"Room Management"})
    public ResponseEntity<RoomDto> updateRoomById(@PathVariable Long roomId, @PathVariable Long hotelId, @RequestBody RoomDto roomDto) {
        return ResponseEntity.ok(roomService.updateRoomById(hotelId, roomId, roomDto));
    }

}
