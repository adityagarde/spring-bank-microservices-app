package com.github.adityagarde.account.cmd.api.commands;

import com.github.adityagarde.cqrs.core.commands.BaseCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WithdrawFundsCommand extends BaseCommand {
    private double amount;
}