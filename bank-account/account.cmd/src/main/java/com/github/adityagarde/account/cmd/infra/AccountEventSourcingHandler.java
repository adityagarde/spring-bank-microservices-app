package com.github.adityagarde.account.cmd.infra;

import com.github.adityagarde.cqrs.core.domain.AggregateRoot;
import com.github.adityagarde.cqrs.core.handlers.EventSourcingHandler;
import com.github.adityagarde.account.cmd.domain.AccountAggregate;
import com.github.adityagarde.cqrs.core.infra.EventStore;
import com.github.adityagarde.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    @Autowired
    private EventStore eventStore;

    @Autowired
    private EventProducer eventProducer;

    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String id) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEvents(id);
        if (events != null && !events.isEmpty()) {
            aggregate.replayEvent(events);
            var latestVersion = events.stream()
                    .map(item -> item.getVersion())
                    .max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }
        return aggregate;
    }

    @Override
    public void republishEvents() {
        var aggregateIds = eventStore.getAggregateIds();
        for (var aggregateId : aggregateIds) {
            var aggregate = getById(aggregateId);
            if (aggregate == null || !aggregate.getActive())
                continue;
            var events = eventStore.getEvents(aggregateId);
            for (var event : events) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }
}
