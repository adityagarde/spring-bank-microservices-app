package com.github.adityagarde.account.cmd.api;

import com.github.adityagarde.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class WithdrawFundsCommand extends BaseCommand {
    private double amount;
}