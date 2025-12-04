package com.playground.core_devis.port.spi;

import java.math.BigDecimal;

public interface PricingConfigProvider {
    BigDecimal getDefaultPrice();
}
