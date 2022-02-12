package com.github.adityagarde.account.query.infra;

import com.github.adityagarde.cqrs.core.domain.BaseEntity;
import com.github.adityagarde.cqrs.core.infra.QueryDispatcher;
import com.github.adityagarde.cqrs.core.queries.BaseQuery;
import com.github.adityagarde.cqrs.core.queries.QueryHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountQueryDispatcher implements QueryDispatcher {

    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public <U extends BaseEntity> List<U> send(BaseQuery query) {
        var handlers = routes.get(query.getClass());
        if (handlers == null || handlers.size() <= 0) {
            throw new RuntimeException("No Query handler was registered!");
        }
        if (handlers.size() > 1) {
            throw new RuntimeException("Cannot send Query to more than one handler!");
        }
        return handlers.get(0).handle(query);
    }
}