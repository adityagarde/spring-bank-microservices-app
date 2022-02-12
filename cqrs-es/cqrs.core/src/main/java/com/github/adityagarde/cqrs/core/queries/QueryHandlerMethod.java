package com.github.adityagarde.cqrs.core.queries;

import com.github.adityagarde.cqrs.core.domain.BaseEntity;

import java.util.List;

@FunctionalInterface
public interface QueryHandlerMethod<T extends BaseQuery> {
    List<BaseEntity> handle(T query);
}