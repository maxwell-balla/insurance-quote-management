package com.playground.core_devis.port.spi;

import java.util.function.Supplier;

/**
 * Port for manage transactions
 * operation : runtime code
 */
public interface UnitOfWorkPort {
    <T> T execute(Supplier<T> operation);
}
