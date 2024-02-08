package br.com.fullcycle.application.ticket;

import java.util.Objects;

import br.com.fullcycle.application.UseCase;
import br.com.fullcycle.domain.customer.CustomerId;
import br.com.fullcycle.domain.event.EventId;
import br.com.fullcycle.domain.event.EventTicketId;
import br.com.fullcycle.domain.event.ticket.Ticket;
import br.com.fullcycle.domain.event.ticket.TicketRepository;

public class CreateTicketForCustomerUseCase
        extends UseCase<CreateTicketForCustomerUseCase.Input, CreateTicketForCustomerUseCase.Output> {

    private final TicketRepository ticketRepository;

    public CreateTicketForCustomerUseCase(final TicketRepository ticketRepository) {
        this.ticketRepository = Objects.requireNonNull(ticketRepository);
    }

    @Override
    public Output execute(Input input) {
        final var aTicket = Ticket.newTicket(EventTicketId.with(input.eventTicketId()), CustomerId.with(input.customerId()), EventId.with(input.eventId()));
        this.ticketRepository.create(aTicket);

        return new Output(aTicket.ticketId().value());
    }

    public record Input(String eventTicketId, String eventId, String customerId) {}
    public record Output(String ticketId) {}
}
