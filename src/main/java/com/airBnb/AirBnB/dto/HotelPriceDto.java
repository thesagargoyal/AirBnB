package com.airBnb.AirBnB.dto;

import com.airBnb.AirBnB.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelPriceDto {
    private Hotel hotel;
    private double price;
}
