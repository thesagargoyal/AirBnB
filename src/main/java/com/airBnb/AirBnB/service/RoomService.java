package com.airBnb.AirBnB.service;

import com.airBnb.AirBnB.dto.RoomDto;

import java.util.List;

public interface RoomService {
    RoomDto createNewRoom(Long hotelId, RoomDto roomDto);
    List<RoomDto> getAllRoomsInAHotel(Long hotelId);
    RoomDto getRoomById(long roomId);
    void deleteRoomById(long roomId);
}
