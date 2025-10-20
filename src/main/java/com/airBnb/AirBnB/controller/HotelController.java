package com.airBnb.AirBnB.controller;

import com.airBnb.AirBnB.dto.BookingDto;
import com.airBnb.AirBnB.dto.HotelDto;
import com.airBnb.AirBnB.dto.HotelReportDto;
import com.airBnb.AirBnB.service.BookingService;
import com.airBnb.AirBnB.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin/hotels")
@RequiredArgsConstructor
@Slf4j
public class HotelController {

    private final HotelService hotelService;
    private final BookingService bookingService;

    @PostMapping
    @Operation(summary = "Create a new hotel", tags = {"Hotel Administration"})
    public ResponseEntity<HotelDto> createNewHotel(@RequestBody HotelDto hotelDto) {
        HotelDto hotel = hotelService.createNewHotel(hotelDto);
        return new ResponseEntity<>(hotel, HttpStatus.CREATED);
    }

    @GetMapping("/{hotelId}")
    @Operation(summary = "Get hotel details by ID", tags = {"Hotel Administration"})
    public ResponseEntity<HotelDto> getHotelById(@PathVariable long hotelId) {
        HotelDto hotel = hotelService.getHotelById(hotelId);
        return ResponseEntity.ok(hotel);
    }

    @PutMapping("/{hotelId}")
    @Operation(summary = "Update hotel details", tags = {"Hotel Administration"})
    public ResponseEntity<HotelDto> updateHotelById(@PathVariable long hotelId, @RequestBody HotelDto hotelDto) {
        HotelDto hotel = hotelService.updateHotelById(hotelId, hotelDto);
        return ResponseEntity.ok(hotel);
    }

    @DeleteMapping("/{hotelId}")
    @Operation(summary = "Delete a hotel", tags = {"Hotel Administration"})
    public ResponseEntity<Void> deleteHotelById(@PathVariable long hotelId) {
        hotelService.deleteHotelById(hotelId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{hotelId}/activate")
    @Operation(summary = "Activate a hotel", tags = {"Hotel Administration"})
    public ResponseEntity<Void> activateHotelById(@PathVariable long hotelId) {
        hotelService.activateHotelById(hotelId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Get all hotels", tags = {"Hotel Administration"})
    public ResponseEntity<List<HotelDto>> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    @GetMapping("/{hotelId}/bookings")
    @Operation(summary = "Get all bookings for a hotel", tags = {"Hotel Administration"})
    public ResponseEntity<List<BookingDto>> getAllBookingsByHotelId(@PathVariable long hotelId) {
        return ResponseEntity.ok(bookingService.getAllBookingsByHotelId(hotelId));
    }

    @GetMapping("/{hotelId}/reports")
    @Operation(summary = "Generate hotel report", tags = {"Hotel Administration"})
    public ResponseEntity<HotelReportDto> generateHotelReport(@PathVariable Long hotelId,
                                                              @RequestParam(required = false) LocalDate startDate,
                                                              @RequestParam(required = false) LocalDate endDate) {
        if(startDate == null) startDate = LocalDate.now().minusMonths(1);
        if(endDate == null) endDate = LocalDate.now();
        return ResponseEntity.ok(bookingService.generateHotelReport(hotelId, startDate, endDate));
    }

}
