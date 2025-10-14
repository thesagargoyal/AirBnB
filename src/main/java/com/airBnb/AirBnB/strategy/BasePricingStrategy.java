package com.airBnb.AirBnB.strategy;

import com.airBnb.AirBnB.entity.Inventory;

import java.math.BigDecimal;

public class BasePricingStrategy implements PricingStrategy {
    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        return inventory.getRoom().getBasePrice();
    }
}
