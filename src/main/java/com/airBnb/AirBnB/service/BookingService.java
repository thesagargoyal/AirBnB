package com.airBnb.AirBnB.service;

import com.airBnb.AirBnB.dto.BookingDto;
import com.airBnb.AirBnB.dto.BookingRequest;
import com.airBnb.AirBnB.dto.GuestDto;

import java.util.List;

public interface BookingService {
    BookingDto initialiseBooking(BookingRequest bookingRequest);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestsList);
}
