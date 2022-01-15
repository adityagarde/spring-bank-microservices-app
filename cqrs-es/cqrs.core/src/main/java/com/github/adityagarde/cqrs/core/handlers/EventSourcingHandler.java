package com.github.adityagarde.cqrs.core.handlers;

import com.github.adityagarde.cqrs.core.domain.AggregateRoot;

public interface EventSourcingHandler<T> {

    void save(AggregateRoot aggregate);

    T getById(String id);

}
