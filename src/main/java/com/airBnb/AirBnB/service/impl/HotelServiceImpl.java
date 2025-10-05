package com.airBnb.AirBnB.service.impl;

import com.airBnb.AirBnB.dto.HotelDto;
import com.airBnb.AirBnB.entity.Hotel;
import com.airBnb.AirBnB.entity.Room;
import com.airBnb.AirBnB.exception.ResourceNotFoundException;
import com.airBnb.AirBnB.repository.HotelRepository;
import com.airBnb.AirBnB.repository.RoomRepository;
import com.airBnb.AirBnB.service.HotelService;
import com.airBnb.AirBnB.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;
    private final RoomRepository roomRepository;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        hotel.setActive(false);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long hotelId) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long hotelId, HotelDto hotelDto) {
        Hotel hotel = hotelRepository
                .findById(hotelId )
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
        modelMapper.map(hotelDto, hotel);
        hotel.setId(hotelId);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    @Transactional
    public void deleteHotelById(Long hotelId) {
        boolean exist = hotelRepository.existsById(hotelId);
        if (!exist) {
            throw new ResourceNotFoundException("Hotel not found with id: " + hotelId);
        }
        // Delete future Inventories
        Hotel hotel = hotelRepository
                .findById(hotelId )
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
        for (Room room : hotel.getRooms()) {
            inventoryService.deleteAllInventories(room);
            roomRepository.deleteById(room.getId());
        }
        hotelRepository.deleteById(hotelId);
    }

    @Override
    @Transactional
    public void activateHotelById(Long hotelId) {
        Hotel hotel = hotelRepository
                .findById(hotelId )
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));

        // TODO: Handle if it is already active

        hotel.setActive(true);

        // If activating hotel for the first time
        for(Room room : hotel.getRooms()){
            inventoryService.initializeRoomForAYear(room);
        }
    }


}
