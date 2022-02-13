package com.github.adityagarde.account.cmd.api.controllers;

import com.github.adityagarde.account.cmd.api.commands.RestoreReadDbCommand;
import com.github.adityagarde.account.common.dto.BaseResponse;
import com.github.adityagarde.cqrs.core.infra.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/restoreReadDb")
public class RestoreReadDbController {
    private final Logger logger = Logger.getLogger(RestoreReadDbController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> restoreReadDb() {

        try {
            commandDispatcher.send(new RestoreReadDbCommand());
            return new ResponseEntity<>(new BaseResponse("Read DB restore request successfully"), HttpStatus.CREATED);
        } catch (IllegalStateException ex) {
            logger.log(Level.WARNING, MessageFormat.format("Client made a bad request -{0}.", ex.toString()));
            return new ResponseEntity<>(new BaseResponse(ex.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            var safeErrorMessage = "Error while processing to restore read DB";
            logger.log(Level.SEVERE, safeErrorMessage, ex);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
