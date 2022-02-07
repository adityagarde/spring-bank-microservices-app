package com.github.adityagarde.cqrs.core.producers;

import com.github.adityagarde.cqrs.core.events.BaseEvent;

public interface EventProducer {
    void produce(String topic, BaseEvent event);
}
