package com.airBnb.AirBnB.controller;

import com.airBnb.AirBnB.dto.BookingDto;
import com.airBnb.AirBnB.dto.GuestDto;
import com.airBnb.AirBnB.dto.ProfileUpdateRequestDto;
import com.airBnb.AirBnB.dto.UserDto;
import com.airBnb.AirBnB.service.BookingService;
import com.airBnb.AirBnB.service.GuestService;
import com.airBnb.AirBnB.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BookingService bookingService;
    private final GuestService guestService;

    @PatchMapping("/profile")
    @Operation(summary = "Update user profile", tags = {"Profile Management"})
    public ResponseEntity<Void> updateProfile(@RequestBody ProfileUpdateRequestDto profileUpdateRequestDto) {
        userService.updateProfile(profileUpdateRequestDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/myBookings")
    @Operation(summary = "Get current user booking", tags = {"Profile Management"})
    public ResponseEntity<List<BookingDto>> getMyBookings() {
        return ResponseEntity.ok(bookingService.geyMyBookings());
    }

    @GetMapping("/profile")
    @Operation(summary = "Get current user profile", tags = {"Profile Management"})
    public ResponseEntity<UserDto> getMyProfile() {
        return ResponseEntity.ok(userService.getMyProfile());
    }

    @GetMapping("/guests")
    @Operation(summary = "Get all guests for current user", tags = {"Guest Management"})
    public ResponseEntity<List<GuestDto>> getAllGuests() {
        return ResponseEntity.ok(guestService.getAllGuests());
    }

    @PostMapping("/guests")
    @Operation(summary = "Add a new guest", tags = {"Guest Management"})
    public ResponseEntity<GuestDto> addNewGuest(@RequestBody GuestDto guestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(guestService.addNewGuest(guestDto));
    }

    @PutMapping("/guests/{guestId}")
    @Operation(summary = "Update guest information", tags = {"Guest Management"})
    public ResponseEntity<Void> updateGuest(@PathVariable Long guestId, @RequestBody GuestDto guestDto) {
        guestService.updateGuest(guestId, guestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("guests/{guestId}")
    @Operation(summary = "Delete a guest", tags = {"Guest Management"})
    public ResponseEntity<Void> deleteGuest(@PathVariable Long guestId) {
        guestService.deleteGuest(guestId);
        return ResponseEntity.noContent().build();
    }

}
