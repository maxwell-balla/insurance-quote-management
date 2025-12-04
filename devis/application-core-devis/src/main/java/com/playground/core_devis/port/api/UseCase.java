package com.playground.core_devis.port.api;

public interface UseCase<TRequest, TResponse>  {
    TResponse execute(TRequest request);
}
