package com.airBnb.AirBnB.dto;

import com.airBnb.AirBnB.entity.User;
import com.airBnb.AirBnB.entity.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class GuestDto {
    private Long id;
    @JsonIgnore
    private User user;
    private String name;
    private Gender gender;
    private Integer age;
}
