package com.github.adityagarde.account.query.api.queries;

import com.github.adityagarde.cqrs.core.domain.BaseEntity;

import java.util.List;

public interface QueryHandler {
    List<BaseEntity> handle(FindAllAccountQuery query);

    List<BaseEntity> handle(FindAccountByIdQuery query);

    List<BaseEntity> handle(FindAccountWithBalanceQuery query);

    List<BaseEntity> handle(FindAccountByHolderQuery query);
}
