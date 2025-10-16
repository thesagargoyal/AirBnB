package com.airBnb.AirBnB.strategy;

import com.airBnb.AirBnB.entity.Inventory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PricingService {

    public BigDecimal calculateDynamicPricing(Inventory inventory){

        PricingStrategy pricingStrategy = new BasePricingStrategy();

        // Apply the additional strategy
        pricingStrategy = new SurgePricingStrategy(pricingStrategy);
        pricingStrategy = new OccupancyPricingStrategy(pricingStrategy);
        pricingStrategy = new UrgencyPricingStrategy(pricingStrategy);
        pricingStrategy = new HolidayPricingStrategy(pricingStrategy);

        return pricingStrategy.calculatePrice(inventory);
    }

    public BigDecimal calculateTotalPrice(List<Inventory> inventoryList, int numberOfRoomsCount){
        return inventoryList.stream()
                .map(this::calculateDynamicPricing)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
