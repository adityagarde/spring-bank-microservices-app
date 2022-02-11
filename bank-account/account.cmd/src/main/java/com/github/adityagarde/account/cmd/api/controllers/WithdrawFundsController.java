package com.github.adityagarde.account.cmd.api.controllers;

import com.github.adityagarde.account.cmd.api.commands.WithdrawFundsCommand;
import com.github.adityagarde.account.common.dto.BaseResponse;
import com.github.adityagarde.cqrs.core.exceptions.AggregateNotFoundException;
import com.github.adityagarde.cqrs.core.infra.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/withdrawFunds")
public class WithdrawFundsController {
    private final Logger logger = Logger.getLogger(WithdrawFundsController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> withdrawFunds(@PathVariable(value = "id") String id,
                                                      @RequestBody WithdrawFundsCommand command) {
        try {
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Withdraw Funds request completed successfully!"), HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException ex) {
            logger.log(Level.WARNING, MessageFormat.format("Client made a bad request -{0}.", ex.toString()));
            return new ResponseEntity<>(new BaseResponse(ex.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            var safeErrorMessage = MessageFormat.format("Error while processing withdraw funds request for - {0}.", id);
            logger.log(Level.SEVERE, safeErrorMessage, ex);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}