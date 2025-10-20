package com.airBnb.AirBnB.service.impl;

import com.airBnb.AirBnB.dto.*;
import com.airBnb.AirBnB.entity.Hotel;
import com.airBnb.AirBnB.entity.Inventory;
import com.airBnb.AirBnB.entity.Room;
import com.airBnb.AirBnB.entity.User;
import com.airBnb.AirBnB.exception.ResourceNotFoundException;
import com.airBnb.AirBnB.repository.HotelMinPriceRepository;
import com.airBnb.AirBnB.repository.InventoryRepository;
import com.airBnb.AirBnB.repository.RoomRepository;
import com.airBnb.AirBnB.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static com.airBnb.AirBnB.util.AppUtils.getCurrentUser;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final HotelMinPriceRepository hotelMinPriceRepository;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    @Override
    public void initializeRoomForAYear(Room room) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusYears(1);

        for (; !today.isAfter(endDate); today = today.plusDays(1)) {
            Inventory inventory = Inventory.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .bookedCount(0)
                    .reservedCount(0)
                    .city(room.getHotel().getCity())
                    .date(today)
                    .price(room.getBasePrice())
                    .surgeFactor(BigDecimal.ONE)
                    .totalCount(room.getTotalCount())
                    .closed(false)
                    .build();
            inventoryRepository.save(inventory);
        }
    }

    @Override
    public void deleteAllInventories(Room room) {
        inventoryRepository.deleteByRoom(room);
    }

    @Override
    public Page<HotelPriceDto> searchHotels(HotelSearchRequest hotelSearchRequest) {
        Pageable page = PageRequest.of(hotelSearchRequest.getPage(), hotelSearchRequest.getSize());
        long dateCount = ChronoUnit.DAYS.between(hotelSearchRequest.getStartDate(), hotelSearchRequest.getEndDate()) + 1;

        return hotelMinPriceRepository
                .findHotelsWithAvailableInventory(hotelSearchRequest.getCity(), hotelSearchRequest.getStartDate(), hotelSearchRequest.getEndDate(), hotelSearchRequest.getRoomsCount(), dateCount, page);

    }

    @Override
    public List<InventoryDto> getAllInventoryByRoom(Long roomId) {
        log.info("Getting all inventory by room for room id {}", roomId);
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + roomId));

        User user = getCurrentUser();

        if (!user.equals(room.getHotel().getOwner())) {
            throw new AccessDeniedException("Yuoh! You are not the owner of this room with id: " + roomId);
        }

        return inventoryRepository
                .findByRoomOrderByDateDesc(room)
                .stream()
                .map(inventory -> modelMapper.map(inventory, InventoryDto.class))
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public void updateInventory(Long roomId, UpdateInventoryRequestDto updateInventoryRequestDto) {
        log.info("Updating all inventory by room for roomwith id: {} between date range: {} - {}", roomId, updateInventoryRequestDto.getStartDate(), updateInventoryRequestDto.getEndDate());
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + roomId));

        User user = getCurrentUser();

        if (!user.equals(room.getHotel().getOwner())) {
            throw new AccessDeniedException("Yuoh! You are not the owner of this room with id: " + roomId);
        }

        inventoryRepository
                .getInventoryAndLockBeforeUpdate(
                        roomId,
                        updateInventoryRequestDto.getStartDate(),
                        updateInventoryRequestDto.getEndDate()
                );

        inventoryRepository.updateInventory(
                roomId,
                updateInventoryRequestDto.getStartDate(),
                updateInventoryRequestDto.getEndDate(),
                updateInventoryRequestDto.getClosed(),
                updateInventoryRequestDto.getSurgeFactor()
        );

    }
}
