package com.playground.core_pricing.domain.model;

public record Profile(int age) {
    public Profile {
        if (age < 0) {
            throw new IllegalArgumentException("Age must be positive");
        }
        if (age > 120) {
            throw new IllegalArgumentException("Age must be realistic");
        }
    }
}
