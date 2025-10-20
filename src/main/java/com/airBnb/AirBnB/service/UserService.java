package com.airBnb.AirBnB.service;

import com.airBnb.AirBnB.dto.ProfileUpdateRequestDto;
import com.airBnb.AirBnB.dto.UserDto;
import com.airBnb.AirBnB.entity.User;

public interface UserService {
    User getUserById(Long id);
    void updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto);
    UserDto getMyProfile();
}
