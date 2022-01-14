package com.github.adityagarde.cqrs.core.infra;

import com.github.adityagarde.cqrs.core.commands.BaseCommand;
import com.github.adityagarde.cqrs.core.commands.CommandHandlerMethod;

public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);

    void send(BaseCommand command);
}
