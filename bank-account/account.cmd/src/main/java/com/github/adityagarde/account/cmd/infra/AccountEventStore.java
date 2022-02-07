package com.github.adityagarde.account.cmd.infra;

import com.github.adityagarde.account.cmd.domain.AccountAggregate;
import com.github.adityagarde.account.cmd.domain.EventStoreRepository;
import com.github.adityagarde.cqrs.core.events.BaseEvent;
import com.github.adityagarde.cqrs.core.events.EventModel;
import com.github.adityagarde.cqrs.core.exceptions.AggregateNotFoundException;
import com.github.adityagarde.cqrs.core.exceptions.ConcurrencyException;
import com.github.adityagarde.cqrs.core.infra.EventStore;
import com.github.adityagarde.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountEventStore implements EventStore {

    @Autowired
    private EventStoreRepository eventStoreRepository;

    @Autowired
    private EventProducer eventProducer;

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            throw new ConcurrencyException();
        }
        int version = expectedVersion;
        for (var event : events) {
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .timestamp(new Date())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();

            var persistedEvent = eventStoreRepository.save(eventModel);
            if (!persistedEvent.getId().isEmpty()) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);

        if (eventStream == null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException("Incorrect accountId provided.");
        }
        return eventStream.stream()
                .map(item -> item.getEventData())
                .collect(Collectors.toList());
    }
}
