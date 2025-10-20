package com.airBnb.AirBnB.controller;

import com.airBnb.AirBnB.dto.BookingDto;
import com.airBnb.AirBnB.dto.BookingRequest;
import com.airBnb.AirBnB.dto.GuestDto;
import com.airBnb.AirBnB.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class HotelBookingController {

    private final BookingService bookingService;

    @PostMapping("/init")
    @Operation(summary = "Initialize a new booking", tags = {"Booking Management"})
    public ResponseEntity<BookingDto> initialiseBooking(@RequestBody BookingRequest bookingRequest){
        return ResponseEntity.ok(bookingService.initialiseBooking(bookingRequest));
    }

    @PostMapping("/{bookingId}/addGuests")
    @Operation(summary = "Add guests to a booking", tags = {"Booking Management"})
    public ResponseEntity<BookingDto> addGuests(@PathVariable Long bookingId, @RequestBody List<GuestDto> guestsList){
        return ResponseEntity.ok(bookingService.addGuests(bookingId, guestsList));
    }

    @PostMapping("/{bookingId}/payment")
    @Operation(summary = "Initiate payment for a booking", tags = {"Booking Management"})
    public ResponseEntity<Map<String, String>> initPayment(@PathVariable Long bookingId) {
        String sessionUrl =  bookingService.initiatePayment(bookingId);
        return ResponseEntity.ok(Map.of("sessionUrl", sessionUrl));
    }

    @PostMapping("/{bookingId}/cancel")
    @Operation(summary = "Cancel a booking", tags = {"Booking Management"})
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{bookingId}/status")
    @Operation(summary = "Get booking status", tags = {"Booking Management"})
    public ResponseEntity<Map<String, String>> getBookingStatus(@PathVariable Long bookingId) {
        return ResponseEntity.ok(Map.of("status", bookingService.getBookingStatus(bookingId)));
    }
}
