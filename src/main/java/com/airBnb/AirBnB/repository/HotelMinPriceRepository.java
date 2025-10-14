package com.airBnb.AirBnB.repository;

import com.airBnb.AirBnB.dto.HotelPriceDto;
import com.airBnb.AirBnB.entity.Hotel;
import com.airBnb.AirBnB.entity.HotelMinPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface HotelMinPriceRepository extends JpaRepository<HotelMinPrice, Long> {

    @Query("""
            SELECT new com.airBnb.AirBnB.dto.HotelPriceDto(i.hotel, AVG(i.price))
                FROM HotelMinPrice i
                    where i.hotel.city = :city
                        AND i.date between :startDate AND :endDate
                            AND i.hotel.active = true
                                GROUP BY i.hotel
            """)
    Page<HotelPriceDto> findHotelsWithAvailableInventory(
            @Param("city") String city,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomsCount") Integer roomsCount,
            @Param("dateCount") Long dateCount,
            Pageable pageable
    );

    Optional<HotelMinPrice> findByHotelAndDate(Hotel hotel, LocalDate date);
}
