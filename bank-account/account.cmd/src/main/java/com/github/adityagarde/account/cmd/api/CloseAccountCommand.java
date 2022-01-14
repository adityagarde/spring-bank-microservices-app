package com.github.adityagarde.account.cmd.api;

import com.github.adityagarde.cqrs.core.commands.BaseCommand;

public class CloseAccountCommand extends BaseCommand {
    public CloseAccountCommand(String id) {
        super(id);
    }
}
