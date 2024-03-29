package br.com.fullcycle.infrastructure.jpa.entities;

import java.util.Objects;
import java.util.UUID;

import br.com.fullcycle.domain.customer.CustomerId;
import br.com.fullcycle.domain.event.EventId;
import br.com.fullcycle.domain.event.EventTicket;
import br.com.fullcycle.domain.event.EventTicketId;
import br.com.fullcycle.domain.event.ticket.TicketId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "EventTicket")
@Table(name = "events_tickets")
public class EventTicketEntity {

    @Id
    private UUID eventTicketId;

    private UUID ticketId;

    private UUID customerId;

    private int ordering;

    @ManyToOne(fetch = FetchType.LAZY)
    private EventEntity event;

    public EventTicketEntity() {
    }

    public EventTicketEntity(
            UUID eventTicketId,
            UUID customerId,
            Integer ordering,
            UUID ticketId,
            EventEntity event
    ) {
        this.eventTicketId = eventTicketId;
        this.customerId = customerId;
        this.ordering = ordering;
        this.ticketId = ticketId;
        this.event = event;
    }

    public static EventTicketEntity of(final EventEntity event, final EventTicket eventTicket) {
        return new EventTicketEntity(
                UUID.fromString(eventTicket.eventTicketId().value()),
                UUID.fromString(eventTicket.customerId().value()),
                eventTicket.ordering(),
                eventTicket.ticketId() != null ? UUID.fromString(eventTicket.ticketId().value()) : null,
                event
        );
    }

    public EventTicket toTicket() {
        return new EventTicket(
                EventTicketId.with(this.eventTicketId.toString()),
                EventId.with(this.event.id().toString()),
                CustomerId.with(this.customerId.toString()),
                this.ticketId != null ? TicketId.with(this.ticketId.toString()) : null,
                this.ordering
        );
    }

    public UUID getEventTicketId() {
        return eventTicketId;
    }

    public void setEventTicketId(UUID eventTicketId) {
        this.eventTicketId = eventTicketId;
    }

    public UUID getTicketId() {
        return ticketId;
    }

    public UUID ticketId() {
        return ticketId;
    }

    public void setTicketId(UUID ticketId) {
        this.ticketId = ticketId;
    }

    public UUID customerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public int ordering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    public EventEntity event() {
        return event;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventTicketEntity that = (EventTicketEntity) o;
        return Objects.equals(eventTicketId, that.eventTicketId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventTicketId);
    }
}
