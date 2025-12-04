package com.playground.core_pricing.domain.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Price {
    private UUID priceId;
    private BigDecimal tarif;
    private ProductType productType;
    private Profile profile;

    public Price(UUID priceId, BigDecimal tarif, ProductType productType, Profile profile) {
        if (productType == null) {
            throw new IllegalArgumentException("Product type can not be null");
        }
        if (tarif != null && tarif.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Tarif must be positive");
        }

        this.priceId = priceId;
        this.tarif = tarif;
        this.productType = productType;
        this.profile = profile;
    }

    public Price(ProductType productType, Profile profile) {
        this(UUID.randomUUID(), null, productType, profile);
    }

    public static Price of(ProductType productType, Profile profile) {
        return new Price(productType, profile);
    }

    public UUID getPriceId() {
        return priceId;
    }

    public BigDecimal getTarif() {
        return tarif;
    }

    public ProductType getProductType() {
        return productType;
    }

    public Profile getProfile() {
        return profile;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Objects.equals(getPriceId(), price.getPriceId()) && Objects.equals(getTarif(), price.getTarif()) && getProductType() == price.getProductType() && Objects.equals(getProfile(), price.getProfile());
    }

    @Override
    public int hashCode() {
        return Objects.hash(priceId, tarif, productType, profile);
    }
}