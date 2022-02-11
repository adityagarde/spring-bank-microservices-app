package com.github.adityagarde.account.cmd.api.controllers;

import com.github.adityagarde.account.cmd.api.commands.CloseAccountCommand;
import com.github.adityagarde.account.common.dto.BaseResponse;
import com.github.adityagarde.cqrs.core.exceptions.AggregateNotFoundException;
import com.github.adityagarde.cqrs.core.infra.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/closeAccount")
public class CloseAccountController {
    private final Logger logger = Logger.getLogger(CloseAccountController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> closeAccount(@PathVariable(value = "id") String id) {
        try {
            commandDispatcher.send(new CloseAccountCommand(id));
            return new ResponseEntity<>(new BaseResponse("Bank account closure request successfully completed!"), HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException ex) {
            logger.log(Level.WARNING, MessageFormat.format("Client made a bad request -{0}.", ex.toString()));
            return new ResponseEntity<>(new BaseResponse(ex.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            var safeErrorMessage = MessageFormat.format("Error while processing close account request for - {0}.", id);
            logger.log(Level.SEVERE, safeErrorMessage, ex);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}