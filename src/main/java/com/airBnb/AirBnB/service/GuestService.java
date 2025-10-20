package com.airBnb.AirBnB.service;

import com.airBnb.AirBnB.dto.GuestDto;

import java.util.List;

public interface GuestService {
    List<GuestDto> getAllGuests();

    GuestDto addNewGuest(GuestDto guestDto);

    void updateGuest(Long guestId, GuestDto guestDto);

    void deleteGuest(Long guestId);
}
