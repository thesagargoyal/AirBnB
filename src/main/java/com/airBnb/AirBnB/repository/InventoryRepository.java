package com.airBnb.AirBnB.repository;

import com.airBnb.AirBnB.entity.Inventory;
import com.airBnb.AirBnB.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    void deleteByRoom(Room room);
}
