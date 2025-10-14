package com.airBnb.AirBnB.strategy;

import com.airBnb.AirBnB.entity.Inventory;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class SurgePricingStrategy implements PricingStrategy {

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        return wrapped.calculatePrice(inventory).multiply(inventory.getSurgeFactor());
    }
}
