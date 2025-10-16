package com.airBnb.AirBnB.service.impl;

import com.airBnb.AirBnB.dto.HotelDto;
import com.airBnb.AirBnB.dto.RoomDto;
import com.airBnb.AirBnB.entity.Hotel;
import com.airBnb.AirBnB.entity.Room;
import com.airBnb.AirBnB.entity.User;
import com.airBnb.AirBnB.exception.ResourceNotFoundException;
import com.airBnb.AirBnB.exception.UnauthorizedException;
import com.airBnb.AirBnB.repository.HotelRepository;
import com.airBnb.AirBnB.repository.RoomRepository;
import com.airBnb.AirBnB.service.InventoryService;
import com.airBnb.AirBnB.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.airBnb.AirBnB.util.AppUtils.getCurrentUser;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;

    @Override
    public RoomDto createNewRoom(Long hotelId, RoomDto roomDto) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));

        User user = getCurrentUser();

        if(!user.equals(hotel.getOwner())) {
            throw new UnauthorizedException("This user of id "+user.getId()+" is not owner of this hotel");
        }

        Room room = modelMapper.map(roomDto, Room.class);
        room.setHotel(hotel);
        room = roomRepository.save(room);

        if(hotel.getActive()){
            inventoryService.initializeRoomForAYear(room);
        }

        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsInAHotel(Long hotelId) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));

        User user = getCurrentUser();

        if(!user.equals(hotel.getOwner())) {
            throw new UnauthorizedException("This user of id "+user.getId()+" is not owner of this hotel");
        }

        return hotel.getRooms()
                .stream()
                .map(room -> modelMapper.map(room, RoomDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(long roomId) {
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + roomId));

        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    @Transactional
    public void deleteRoomById(long roomId) {
        boolean isExist = roomRepository.existsById(roomId);
        if (!isExist) {
            throw new ResourceNotFoundException("Room not found with id: " + roomId);
        }
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + roomId));

        User user = getCurrentUser();

        if(!user.equals(room.getHotel().getOwner())) {
            throw new UnauthorizedException("This user of id "+user.getId()+" is not owner of this room");
        }


        // Delete future Inventory for this room
        inventoryService.deleteAllInventories(room);
        roomRepository.deleteById(roomId);
    }
}
