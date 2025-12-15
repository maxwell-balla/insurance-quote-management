package com.playground.core_devis.domain.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Quote {

    private UUID id;
    private double capital;
    private int duration;
    private Status status;
    private UUID customerId;
    private ProductType productType;
    private int age;
    private BigDecimal tarif;

    public static Quote create(UUID customerId, ProductType productType, int age,
                               double capital, int duration, BigDecimal tarif) {
        return new Quote(capital, duration, Status.CREATED, customerId, productType, age, tarif);
    }

    public Quote withId(UUID id) {
        return new Quote(id, this.capital, this.duration, this.status,
                this.customerId, this.productType, this.age, this.tarif);
    }

    public QuoteSnapshot snapshot() {
        return new QuoteSnapshot(id, customerId, productType, age, capital, duration, status, tarif);
    }


    // @EqualsAndHashCode
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Quote quote = (Quote) o;
        return Double.compare(this.capital, quote.capital) == 0
                && this.duration == quote.duration
                && this.age == quote.age
                && Objects.equals(this.id, quote.id)
                && this.status == quote.status
                && Objects.equals(this.customerId, quote.customerId)
                && this.productType == quote.productType
                && Objects.equals(this.tarif, quote.tarif);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.capital, this.duration, this.status,
                this.customerId, this.productType, this.age, this.tarif);
    }

    // Constructor
    public Quote(UUID id, double capital, int duration, Status status, UUID customerId, ProductType productType, int age, BigDecimal tarif) {

        this.id = Objects.requireNonNull(id, "Quote id must not be null");
        this.customerId = Objects.requireNonNull(customerId, "Customer id must not be null");
        this.productType = Objects.requireNonNull(productType, "Product type must not be null");
        this.status = Objects.requireNonNull(status, "Status must not be null");

        if (capital <= 0) {
            throw new IllegalArgumentException("Capital must be positive");
        }
        if (duration <= 0) {
            throw new IllegalArgumentException("Duration must be positive");
        }
        if (age < 0 || age > 120) {
            throw new IllegalArgumentException("Age must be between 0 and 120");
        }
        if (tarif == null || tarif.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Tarif must be positive");
        }

        this.capital = capital;
        this.duration = duration;
        this.age = age;
        this.tarif = tarif;
    }

    private Quote(double capital, int duration, Status status, UUID customerId, ProductType productType, int age, BigDecimal tarif) {
        this.customerId = Objects.requireNonNull(customerId, "Customer id must not be null");
        this.productType = Objects.requireNonNull(productType, "Product type must not be null");
        this.status = Objects.requireNonNull(status, "Status must not be null");

        if (capital <= 0) {
            throw new IllegalArgumentException("Capital must be positive");
        }
        if (duration <= 0) {
            throw new IllegalArgumentException("Duration must be positive");
        }
        if (age < 0 || age > 120) {
            throw new IllegalArgumentException("Age must be between 0 and 120");
        }
        if (tarif == null || tarif.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Tarif must be positive");
        }

        this.capital = capital;
        this.duration = duration;
        this.age = age;
        this.tarif = tarif;
    }
}