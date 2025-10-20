package com.airBnb.AirBnB.service.impl;

import com.airBnb.AirBnB.dto.GuestDto;
import com.airBnb.AirBnB.entity.Guest;
import com.airBnb.AirBnB.entity.User;
import com.airBnb.AirBnB.exception.ResourceNotFoundException;
import com.airBnb.AirBnB.repository.GuestRepository;
import com.airBnb.AirBnB.service.GuestService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.airBnb.AirBnB.util.AppUtils.getCurrentUser;

@Service
@RequiredArgsConstructor
@Slf4j
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<GuestDto> getAllGuests() {
        User user = getCurrentUser();
        List<Guest> guestList = guestRepository.findByUser(user);
        return guestList.stream().map(guest -> modelMapper.map(guest, GuestDto.class)).collect(Collectors.toList());
    }

    @Override
    public GuestDto addNewGuest(GuestDto guestDto) {
        User user = getCurrentUser();
        guestDto.setUser(user);
        Guest guest = modelMapper.map(guestDto, Guest.class);
        return modelMapper.map(guestRepository.save(guest), GuestDto.class);
    }

    @Override
    public void updateGuest(Long guestId, GuestDto guestDto) {
        Guest guest = guestRepository
                .findById(guestId)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with id " + guestId));

        User user = getCurrentUser();
        if(!user.equals(guest.getUser())) throw new AccessDeniedException("You are not the owner of this guest");

        if(guestDto.getName() != null) guest.setName(guestDto.getName());
        if(guestDto.getGender() != null) guest.setGender(guestDto.getGender());
        if(guestDto.getAge() != null) guest.setAge(guestDto.getAge());
        guestRepository.save(guest);
    }

    @Override
    public void deleteGuest(Long guestId) {
        log.info("Deleting guest with ID: {}", guestId);
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new EntityNotFoundException("Guest not found"));

        User user = getCurrentUser();
        if(!user.equals(guest.getUser())) throw new AccessDeniedException("You are not the owner of this guest");
        guestRepository.deleteById(guestId);
        log.info("Guest with ID: {} deleted successfully", guestId);
    }
}
