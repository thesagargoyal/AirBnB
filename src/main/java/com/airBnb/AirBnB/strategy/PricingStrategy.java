package com.airBnb.AirBnB.strategy;

import com.airBnb.AirBnB.entity.Inventory;

import java.math.BigDecimal;

public interface PricingStrategy {

    BigDecimal calculatePrice(Inventory inventory);

}
