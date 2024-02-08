package br.com.fullcycle.application.event;

import java.time.Instant;
import java.util.Objects;

import br.com.fullcycle.application.UseCase;
import br.com.fullcycle.domain.customer.CustomerId;
import br.com.fullcycle.domain.customer.CustomerRepository;
import br.com.fullcycle.domain.event.EventId;
import br.com.fullcycle.domain.event.EventRepository;
import br.com.fullcycle.domain.event.EventTicket;
import br.com.fullcycle.domain.exceptions.ValidationException;

public class SubscribeCustomerToEventUseCase extends UseCase<SubscribeCustomerToEventUseCase.Input, SubscribeCustomerToEventUseCase.Output> {

    private final EventRepository eventRepository;
    private final CustomerRepository customerRepository;

    public SubscribeCustomerToEventUseCase(
            final EventRepository eventRepository,
            final CustomerRepository customerRepository) {
        this.eventRepository = Objects.requireNonNull(eventRepository);
        this.customerRepository = Objects.requireNonNull(customerRepository);
    }

    @Override
    public Output execute(final Input input) {
        var customer = customerRepository.customerOfId(CustomerId.with(input.customerId))
                                      .orElseThrow(() -> new ValidationException("Customer not found"));

        var event = eventRepository.eventOfId(EventId.with(input.eventId))
                                .orElseThrow(() -> new ValidationException("Event not found"));

        final EventTicket ticket = event.reserveTicket(customer.customerId());

        eventRepository.update(event);

        return new Output(event.eventId().value(), ticket.eventTicketId().value(), Instant.now());
    }

    public record Input(String eventId, String customerId) {}
    public record Output(String eventId, String eventTicketId, Instant reservationDate) {}
}
