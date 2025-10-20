package com.airBnb.AirBnB.service.impl;

import com.airBnb.AirBnB.dto.BookingDto;
import com.airBnb.AirBnB.dto.HotelDto;
import com.airBnb.AirBnB.dto.HotelInfoDto;
import com.airBnb.AirBnB.dto.RoomDto;
import com.airBnb.AirBnB.entity.Booking;
import com.airBnb.AirBnB.entity.Hotel;
import com.airBnb.AirBnB.entity.Room;
import com.airBnb.AirBnB.entity.User;
import com.airBnb.AirBnB.exception.ResourceNotFoundException;
import com.airBnb.AirBnB.exception.UnauthorizedException;
import com.airBnb.AirBnB.repository.BookingRepository;
import com.airBnb.AirBnB.repository.HotelRepository;
import com.airBnb.AirBnB.repository.RoomRepository;
import com.airBnb.AirBnB.service.HotelService;
import com.airBnb.AirBnB.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.airBnb.AirBnB.util.AppUtils.getCurrentUser;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        hotel.setActive(false);
        hotel.setOwner(getCurrentUser());
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long hotelId) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));

        User user = getCurrentUser();

        if(!user.equals(hotel.getOwner())) {
            throw new UnauthorizedException("This user of id "+user.getId()+" is not owner of this hotel");
        }

        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long hotelId, HotelDto hotelDto) {
        Hotel hotel = hotelRepository
                .findById(hotelId )
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));

        User user = getCurrentUser();

        if(!user.equals(hotel.getOwner())) {
            throw new UnauthorizedException("This user of id "+user.getId()+" is not owner of this hotel");
        }

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
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));

        User user = getCurrentUser();

        if(!user.equals(hotel.getOwner())) {
            throw new UnauthorizedException("This user of id "+user.getId()+" is not owner of this hotel");
        }

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

        User user = getCurrentUser();

        if(!user.equals(hotel.getOwner())) {
            throw new UnauthorizedException("This user of id "+user.getId()+" is not owner of this hotel");
        }

        // TODO: Handle if it is already active
        hotel.setActive(true);

        // If activating hotel for the first time
        for(Room room : hotel.getRooms()){
            inventoryService.initializeRoomForAYear(room);
        }
    }

    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId) {
        Hotel hotel = hotelRepository
                .findById(hotelId )
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
        List<RoomDto> rooms = hotel.getRooms()
                .stream()
                .map(room -> modelMapper.map(room, RoomDto.class))
                .toList();
        return new HotelInfoDto(modelMapper.map(hotel, HotelDto.class), rooms);
    }

    @Override
    public List<HotelDto> getAllHotels() {
        User user = getCurrentUser();
        List<Hotel> hotels = hotelRepository.findHotelsByOwner(user);
        return hotels.stream().map(hotel -> modelMapper.map(hotel, HotelDto.class)).collect(Collectors.toList());
    }

}
