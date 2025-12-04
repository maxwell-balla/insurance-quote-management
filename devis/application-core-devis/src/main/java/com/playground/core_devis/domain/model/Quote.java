package com.playground.core_devis.domain.model;

import com.playground.core_devis.usecase.createquote.CreateQuoteRequest;

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

    public static Quote of(CreateQuoteRequest cmd, BigDecimal tarif) {
        return new Quote(cmd.capital(), cmd.duration(), Status.CREATED, cmd.customerId(), cmd.productType(), cmd.profil().age(), tarif);
    }

    // @Getter
    public UUID getId() {
        return id;
    }

    public double getCapital() {
        return capital;
    }

    public int getDuration() {
        return duration;
    }

    public Status getStatus() {
        return status;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public ProductType getProductType() {
        return productType;
    }

    public int getAge() {
        return age;
    }

    public BigDecimal getTarif() {
        return tarif;
    }

    // @Setter
    public void setId(UUID id) {
        this.id = id;
    }

    // @EqualsAndHashCode
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Quote quote = (Quote) o;
        return Double.compare(getCapital(), quote.getCapital()) == 0 && getDuration() == quote.getDuration() && getAge() == quote.getAge() && Objects.equals(getId(), quote.getId()) && getStatus() == quote.getStatus() && Objects.equals(getCustomerId(), quote.getCustomerId()) && getProductType() == quote.getProductType() && Objects.equals(getTarif(), quote.getTarif());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCapital(), getDuration(), getStatus(), getCustomerId(), getProductType(), getAge(), getTarif());
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

    public Quote(double capital, int duration, Status status, java.util.UUID customerId, ProductType productType, int age, BigDecimal tarif) {
        this.capital = capital;
        this.duration = duration;
        this.status = status;
        this.customerId = customerId;
        this.productType = productType;
        this.age = age;
        this.tarif = tarif;
    }
}