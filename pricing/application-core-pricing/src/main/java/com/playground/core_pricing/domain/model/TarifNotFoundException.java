package com.playground.core_pricing.domain.model;

public class TarifNotFoundException extends RuntimeException {
    public TarifNotFoundException(String message) {
        super(message);
    }
}
