package com.github.adityagarde.account.cmd.infra;

import com.github.adityagarde.cqrs.core.domain.AggregateRoot;
import com.github.adityagarde.cqrs.core.handlers.EventSourcingHandler;
import com.github.adityagarde.account.cmd.domain.AccountAggregate;
import com.github.adityagarde.cqrs.core.infra.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    @Autowired
    private EventStore eventStore;

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
            int latestVersion = events.stream()
                    .map(item -> item.getVersion())
                    .max(Comparator.naturalOrder())
                    .get();
            aggregate.setVersion(latestVersion);
        }
        return aggregate;
    }
}
