package com.airBnb.AirBnB.service;

import com.airBnb.AirBnB.dto.HotelPriceDto;
import com.airBnb.AirBnB.dto.HotelSearchRequest;
import com.airBnb.AirBnB.dto.InventoryDto;
import com.airBnb.AirBnB.dto.UpdateInventoryRequestDto;
import com.airBnb.AirBnB.entity.Room;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InventoryService {
    void initializeRoomForAYear(Room room);
    void deleteAllInventories(Room room);

    Page<HotelPriceDto> searchHotels(HotelSearchRequest hotelSearchRequest);

    List<InventoryDto> getAllInventoryByRoom(Long roomId);

    void updateInventory(Long roomId, UpdateInventoryRequestDto updateInventoryRequestDto);
}
