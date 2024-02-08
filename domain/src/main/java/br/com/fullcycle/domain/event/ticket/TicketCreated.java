package br.com.fullcycle.domain.event.ticket;

import java.time.Instant;
import java.util.UUID;

import br.com.fullcycle.domain.DomainEvent;
import br.com.fullcycle.domain.customer.CustomerId;
import br.com.fullcycle.domain.event.EventId;
import br.com.fullcycle.domain.event.EventTicketId;

public record TicketCreated(
        String domainEventId,
        String type,
        String ticketId,
        String eventTicketId,
        String eventId,
        String customerId,
        Instant occurredOn
) implements DomainEvent {

    public TicketCreated(TicketId ticketId, EventTicketId eventTicketId, EventId eventId, CustomerId customerId) {
        this(UUID.randomUUID().toString(), "ticket.created", ticketId.value(), eventTicketId.value(), eventId.value(), customerId.value(), Instant.now());
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
