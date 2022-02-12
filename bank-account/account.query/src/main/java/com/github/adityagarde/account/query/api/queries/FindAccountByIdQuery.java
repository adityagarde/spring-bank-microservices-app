package com.github.adityagarde.account.query.api.queries;

import com.github.adityagarde.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FindAccountByIdQuery extends BaseQuery {
    private String id;
}
