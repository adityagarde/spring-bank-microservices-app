package com.github.adityagarde.account.query.api.controllers;

import com.github.adityagarde.account.query.api.dto.AccountLookupResponse;
import com.github.adityagarde.account.query.api.dto.EqualityType;
import com.github.adityagarde.account.query.api.queries.FindAccountByHolderQuery;
import com.github.adityagarde.account.query.api.queries.FindAccountByIdQuery;
import com.github.adityagarde.account.query.api.queries.FindAccountWithBalanceQuery;
import com.github.adityagarde.account.query.api.queries.FindAllAccountQuery;
import com.github.adityagarde.account.query.domain.BankAccount;
import com.github.adityagarde.cqrs.core.infra.QueryDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/bankAccountLookup")
public class AccountLookupController {

    private final Logger logger = Logger.getLogger(AccountLookupController.class.getName());

    @Autowired
    private QueryDispatcher queryDispatcher;

    @GetMapping(path = "/")
    public ResponseEntity<AccountLookupResponse> getAllAccounts() {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountQuery());
            if (accounts == null || accounts.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response
                    = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Successfully returned {0} bank accounts(s).", accounts.size()))
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            var safeErrorMessage = "Failed to fetch all accounts details!";
            logger.log(Level.SEVERE, safeErrorMessage, ex);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byId/{id}")
    public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable(value = "id") String id) {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByIdQuery(id));
            if (accounts == null || accounts.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response
                    = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message("Successfully returned bank accounts.")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            var safeErrorMessage = "Failed to fetch given accounts details!";
            logger.log(Level.SEVERE, safeErrorMessage, ex);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byHolder/{accountHolder}")
    public ResponseEntity<AccountLookupResponse> getAccountByHolder(@PathVariable(value = "accountHolder") String accountHolder) {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByHolderQuery(accountHolder));
            if (accounts == null || accounts.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response
                    = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message("Successfully returned bank accounts.")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            var safeErrorMessage = "Failed to fetch accounts details using Account Holder given!";
            logger.log(Level.SEVERE, safeErrorMessage, ex);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/withBalance/{equalityType}/{balance}")
    public ResponseEntity<AccountLookupResponse> getAccountsWithBalance(@PathVariable(value = "equalityType") EqualityType equalityType,
                                                                        @PathVariable(value = "balance") double balance) {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountWithBalanceQuery(equalityType, balance));
            if (accounts == null || accounts.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response
                    = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Successfully returned {0} bank accounts(s).", accounts.size()))
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            var safeErrorMessage = "Failed to fetch accounts details using Balance details!";
            logger.log(Level.SEVERE, safeErrorMessage, ex);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
