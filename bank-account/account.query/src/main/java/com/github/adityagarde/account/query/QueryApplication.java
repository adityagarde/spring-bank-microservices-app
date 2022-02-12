package com.github.adityagarde.account.query;

import com.github.adityagarde.account.query.api.queries.*;
import com.github.adityagarde.cqrs.core.infra.QueryDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class QueryApplication {

    @Autowired
    private QueryDispatcher queryDispatcher;

    @Autowired
    private QueryHandler queryHandler;

    public static void main(String[] args) {
        SpringApplication.run(QueryApplication.class, args);
    }

    @PostConstruct
    public void registerHandlers() {
        queryDispatcher.registerHandler(FindAllAccountQuery.class, query -> queryHandler.handle(query));
        queryDispatcher.registerHandler(FindAccountByIdQuery.class, query -> queryHandler.handle(query));
        queryDispatcher.registerHandler(FindAccountByHolderQuery.class, query -> queryHandler.handle(query));
        queryDispatcher.registerHandler(FindAccountWithBalanceQuery.class, query -> queryHandler.handle(query));
    }

}