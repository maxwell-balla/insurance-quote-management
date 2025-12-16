package com.playground.core_pricing.domain.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Price {
    private UUID priceId;
    private BigDecimal tarif;
    private ProductType productType;
    private Profil profil;

    public Price(UUID priceId, BigDecimal tarif, ProductType productType, Profil profil) {
        if (productType == null) {
            throw new IllegalArgumentException("Product type can not be null");
        }
        if (tarif != null && tarif.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Tarif must be positive");
        }

        this.priceId = priceId;
        this.tarif = tarif;
        this.productType = productType;
        this.profil = profil;
    }

    public Price(ProductType productType, Profil profil) {
        this(UUID.randomUUID(), null, productType, profil);
    }

    public static Price of(ProductType productType, Profil profil) {
        return new Price(productType, profil);
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

    public Profil getProfil() {
        return profil;
    }

    public PriceSnapshot snapshot() {
        return new PriceSnapshot(priceId, tarif, productType, profil.age());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Objects.equals(getPriceId(), price.getPriceId()) && Objects.equals(getTarif(), price.getTarif()) && getProductType() == price.getProductType() && Objects.equals(getProfil(), price.getProfil());
    }

    @Override
    public int hashCode() {
        return Objects.hash(priceId, tarif, productType, profil);
    }
}