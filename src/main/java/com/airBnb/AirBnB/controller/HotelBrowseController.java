package com.airBnb.AirBnB.controller;

import com.airBnb.AirBnB.dto.HotelDto;
import com.airBnb.AirBnB.dto.HotelInfoDto;
import com.airBnb.AirBnB.dto.HotelPriceDto;
import com.airBnb.AirBnB.dto.HotelSearchRequest;
import com.airBnb.AirBnB.service.HotelService;
import com.airBnb.AirBnB.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelBrowseController {

    private final InventoryService inventoryService;
    private final HotelService hotelService;

    @GetMapping(path = "/search")
    @Operation(summary = "Search for available hotels", tags = {"Hotel Browsing"})
    public ResponseEntity<Page<HotelPriceDto>> searchHotels(@RequestBody HotelSearchRequest hotelSearchRequest) {
        Page<HotelPriceDto> page = inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{hotelId}/info")
    @Operation(summary = "Get hotel information by ID", tags = {"Hotel Browsing"})
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable Long hotelId) {
        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }

}
