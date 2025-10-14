package com.airBnb.AirBnB.repository;

import com.airBnb.AirBnB.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
