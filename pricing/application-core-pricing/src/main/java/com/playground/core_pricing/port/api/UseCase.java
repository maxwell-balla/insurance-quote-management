package com.playground.core_pricing.port.api;

public interface UseCase<TRequest, TResponse>  {
    TResponse execute(TRequest request);
}
