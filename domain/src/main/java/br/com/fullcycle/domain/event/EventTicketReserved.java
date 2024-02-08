package br.com.fullcycle.domain.event;

import java.time.Instant;
import java.util.UUID;

import br.com.fullcycle.domain.DomainEvent;
import br.com.fullcycle.domain.customer.CustomerId;

public record EventTicketReserved(
        String domainEventId,
        String type,
        String eventTicketId,
        String eventId,
        String customerId,
        Instant occurredOn
) implements DomainEvent {

    public EventTicketReserved(EventTicketId eventTicketId, EventId eventId, CustomerId customerId) {
        this(UUID.randomUUID().toString(), "event-ticket.reserved", eventTicketId.value(), eventId.value(), customerId.value(), Instant.now());
    }
    @Override
    public String type() {
        return this.type;
    }

    @Override
    public Instant occurredOn() {
        return this.occurredOn;
    }
}
