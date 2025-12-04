package com.playground.adapter_devis.transaction;

import com.playground.core_devis.port.spi.UnitOfWorkPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Component
public class UnitOfWorkSpringAdapter implements UnitOfWorkPort {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional
    public <T> T execute(Supplier<T> operation) {
        log.debug("Transactional started");
        return operation.get();
    }
}