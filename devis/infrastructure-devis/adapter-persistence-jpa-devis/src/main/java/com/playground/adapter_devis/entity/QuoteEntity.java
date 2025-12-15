package com.playground.adapter_devis.entity;

import com.playground.core_devis.domain.model.ProductType;
import com.playground.core_devis.domain.model.QuoteSnapshot;
import com.playground.core_devis.domain.model.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quotes")
public class QuoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "capital", nullable = false)
    private double capital;

    @Column(name = "duration", nullable = false)
    private int duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", nullable = false)
    private ProductType productType;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "tarif", nullable = false)
    private BigDecimal tarif;

    public static QuoteEntity fromSnapshot(QuoteSnapshot snapshot) {
        return new QuoteEntity(
                snapshot.id(),
                snapshot.capital(),
                snapshot.duration(),
                snapshot.status(),
                snapshot.customerId(),
                snapshot.productType(),
                snapshot.age(),
                snapshot.tarif()
        );
    }

    public QuoteSnapshot toSnapshot() {
        return new QuoteSnapshot(
                id,
                customerId,
                productType,
                age,
                capital,
                duration,
                status,
                tarif
        );
    }
}
