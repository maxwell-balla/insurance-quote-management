package com.playground.core_devis.utils;

import com.playground.core_devis.port.spi.UnitOfWorkPort;

import java.util.function.Supplier;

public class FakeUnitOfWork implements UnitOfWorkPort {

    @Override
    public <T> T execute(Supplier<T> operation) {
        return operation.get();
    }
}