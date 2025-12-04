package com.playground.core_pricing.utils;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Utility class grouping constants common to tests.*/
public final class TestConstantPricing {

    private TestConstantPricing(){
        // Prevents instantiation
    }

    public static final UUID PRICE_ID = UUID.fromString("fe2b0c4a-1f3d-4b8c-9f5d-7a2e6f3b5c1b");

    public static final int AGE = 33;
    public static final BigDecimal TARIF = new BigDecimal("600.00");
}
