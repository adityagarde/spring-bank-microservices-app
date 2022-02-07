package com.github.adityagarde.account.query.infra.handlers;

import com.github.adityagarde.account.common.events.AccountClosedEvent;
import com.github.adityagarde.account.common.events.AccountOpenedEvent;
import com.github.adityagarde.account.common.events.FundsDepositedEvent;
import com.github.adityagarde.account.common.events.FundsWithdrawnEvent;
import com.github.adityagarde.account.query.domain.AccountRepository;
import com.github.adityagarde.account.query.domain.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountEventHandler implements EventHandler {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void on(AccountOpenedEvent event) {
        var bankAccount =
                BankAccount.builder()
                        .id(event.getId())
                        .accountHolder(event.getAccountHolder())
                        .creationDate(event.getCreatedDate())
                        .accountType(event.getAccountType())
                        .balance(event.getOpeningBalance())
                        .build();

        accountRepository.save(bankAccount);

    }

    @Override
    public void on(FundsDepositedEvent event) {
        var bankAccount =
                accountRepository.findById(event.getId());

        if (bankAccount.isEmpty()) return;

        var currentBalance = bankAccount.get().getBalance();
        var latestBalance = currentBalance + event.getAmount();

        bankAccount.get().setBalance(latestBalance);
        accountRepository.save(bankAccount.get());

    }

    @Override
    public void on(FundsWithdrawnEvent event) {
        var bankAccount =
                accountRepository.findById(event.getId());

        if (bankAccount.isEmpty()) return;

        var currentBalance = bankAccount.get().getBalance();
        var latestBalance = currentBalance - event.getAmount();

        bankAccount.get().setBalance(latestBalance);
        accountRepository.save(bankAccount.get());

    }

    @Override
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());

    }
}
