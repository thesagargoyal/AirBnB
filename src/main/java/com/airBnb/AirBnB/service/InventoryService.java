package com.airBnb.AirBnB.service;

import com.airBnb.AirBnB.dto.HotelDto;
import com.airBnb.AirBnB.dto.HotelSearchRequest;
import com.airBnb.AirBnB.entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {
    void initializeRoomForAYear(Room room);
    void deleteAllInventories(Room room);

    Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
