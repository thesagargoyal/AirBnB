package com.airBnb.AirBnB.dto;

import com.airBnb.AirBnB.entity.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;
}
