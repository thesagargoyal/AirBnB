package com.airBnb.AirBnB.service;

import com.airBnb.AirBnB.dto.HotelDto;
import com.airBnb.AirBnB.dto.HotelInfoDto;
import com.airBnb.AirBnB.entity.Hotel;

public interface HotelService {

    HotelDto createNewHotel(HotelDto hotelDto);
    HotelDto getHotelById(Long hotelId);
    HotelDto updateHotelById(Long hotelId, HotelDto hotelDto);
    void deleteHotelById(Long hotelId);
    void activateHotelById(Long hotelId);
    HotelInfoDto getHotelInfoById(Long hotelId);
}
