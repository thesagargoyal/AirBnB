package com.airBnb.AirBnB.repository;

import com.airBnb.AirBnB.entity.Hotel;
import com.airBnb.AirBnB.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findHotelsByOwner(User owner);
}
