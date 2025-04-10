package com.boooking.awayhome.strategy;

import com.boooking.awayhome.entity.Inventory;

import java.math.BigDecimal;

public interface PricingStr5ategy {
    BigDecimal calculatePrice(Inventory inventory);
}
