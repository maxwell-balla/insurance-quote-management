package com.playground.core_devis.utils;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Utility class grouping constants common to tests.*/
public final class TestConstantDevis {

    private TestConstantDevis() {
        // Prevents instantiation
    }

    public static final UUID QUOTE_ID = UUID.fromString("fe2b0c4a-1f3d-4b8c-9f5d-7a2e6f3b5c1b");
    public static final UUID CUSTOMER_ID = UUID.fromString("d64a968f-4ae2-4f29-9a56-c5a9eeb9642a");

    public static final double CAPITAL = 150_000;
    public static final BigDecimal TARIF = BigDecimal.valueOf(6000);
    public static final int AGE = 33;
    public static final int DURATION = 33;
    public static final BigDecimal DEFAULT_TARIF = BigDecimal.valueOf(1000.00);
}