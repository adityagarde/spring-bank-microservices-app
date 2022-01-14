package com.github.adityagarde.account.cmd.api;

import com.github.adityagarde.account.common.dto.AccountType;
import com.github.adityagarde.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class OpenAccountCommand extends BaseCommand {
    private String accountHolder;
    private AccountType accountType;
    private double openingBalance;
}
