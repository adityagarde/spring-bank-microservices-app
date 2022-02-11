package com.github.adityagarde.account.query.infra.consumers;

import com.github.adityagarde.account.common.events.AccountClosedEvent;
import com.github.adityagarde.account.common.events.AccountOpenedEvent;
import com.github.adityagarde.account.common.events.FundsDepositedEvent;
import com.github.adityagarde.account.common.events.FundsWithdrawnEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {

    void consume(@Payload AccountOpenedEvent event, Acknowledgment ack);

    void consume(@Payload FundsDepositedEvent event, Acknowledgment ack);

    void consume(@Payload FundsWithdrawnEvent event, Acknowledgment ack);

    void consume(@Payload AccountClosedEvent event, Acknowledgment ack);
}
