package com.airBnb.AirBnB.repository;

import com.airBnb.AirBnB.entity.Guest;
import com.airBnb.AirBnB.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    List<Guest> findByUser(User user);
}
