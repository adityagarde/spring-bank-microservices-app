package com.github.adityagarde.account.query.infra.handlers;

import com.github.adityagarde.account.common.events.AccountClosedEvent;
import com.github.adityagarde.account.common.events.AccountOpenedEvent;
import com.github.adityagarde.account.common.events.FundsDepositedEvent;
import com.github.adityagarde.account.common.events.FundsWithdrawnEvent;

public interface EventHandler {

    void on(AccountOpenedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWithdrawnEvent event);
    void on(AccountClosedEvent event);
}
