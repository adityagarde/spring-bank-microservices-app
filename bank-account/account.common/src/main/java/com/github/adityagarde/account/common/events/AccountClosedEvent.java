package com.github.adityagarde.account.common.events;

import com.github.adityagarde.cqrs.core.events.BaseEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AccountClosedEvent extends BaseEvent {
}
