package com.github.adityagarde.account.query.api.queries;

import com.github.adityagarde.account.query.api.dto.EqualityType;
import com.github.adityagarde.cqrs.core.queries.BaseQuery;

public class FindAccountWithBalanceQuery extends BaseQuery {
    private EqualityType equalityType;
    private double balance;
}
