package com.airBnb.AirBnB.repository;

import com.airBnb.AirBnB.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
