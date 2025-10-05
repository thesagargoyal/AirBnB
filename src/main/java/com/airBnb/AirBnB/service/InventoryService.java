package com.airBnb.AirBnB.service;

import com.airBnb.AirBnB.entity.Room;

public interface InventoryService {
    void initializeRoomForAYear(Room room);
    void deleteAllInventories(Room room);
}
