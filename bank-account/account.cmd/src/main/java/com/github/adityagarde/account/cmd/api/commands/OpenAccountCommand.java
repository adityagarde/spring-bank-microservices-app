package com.github.adityagarde.account.cmd.api.commands;

import com.github.adityagarde.account.common.dto.AccountType;
import com.github.adityagarde.cqrs.core.commands.BaseCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OpenAccountCommand extends BaseCommand {
    private String accountHolder;
    private AccountType accountType;
    private double openingBalance;
}
