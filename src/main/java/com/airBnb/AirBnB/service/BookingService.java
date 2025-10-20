package com.airBnb.AirBnB.service;

import com.airBnb.AirBnB.dto.BookingDto;
import com.airBnb.AirBnB.dto.BookingRequest;
import com.airBnb.AirBnB.dto.GuestDto;
import com.airBnb.AirBnB.dto.HotelReportDto;
import com.stripe.model.Event;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    BookingDto initialiseBooking(BookingRequest bookingRequest);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestsList);

    String initiatePayment(Long bookingId);

    void capturePayment(Event event);

    void cancelBooking(Long bookingId);

    String getBookingStatus(Long bookingId);

    List<BookingDto> getAllBookingsByHotelId(long hotelId);

    HotelReportDto generateHotelReport(Long hotelId, LocalDate startDate, LocalDate endDate);

    List<BookingDto> geyMyBookings();
}
