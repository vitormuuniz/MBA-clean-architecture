package br.com.fullcycle.infrastructure.rest;

import br.com.fullcycle.application.event.CreateEventUseCase;
import br.com.fullcycle.application.event.SubscribeCustomerToEventUseCase;
import br.com.fullcycle.domain.exceptions.ValidationException;
import br.com.fullcycle.infrastructure.dtos.NewEventDTO;
import br.com.fullcycle.infrastructure.dtos.SubscribeDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;

import static org.springframework.http.HttpStatus.CREATED;

//Adapter
@RestController
@RequestMapping(value = "events")
public class EventController {

    private final CreateEventUseCase createEventUseCase;
    private final SubscribeCustomerToEventUseCase subscribeCustomerToEventUseCase;

    public EventController(final CreateEventUseCase createEventUseCase, final SubscribeCustomerToEventUseCase subscribeCustomerToEventUseCase) {
        this.createEventUseCase = Objects.requireNonNull(createEventUseCase);
        this.subscribeCustomerToEventUseCase = Objects.requireNonNull(subscribeCustomerToEventUseCase);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ResponseEntity<?> create(@RequestBody NewEventDTO input) {
        try {
            final var output = createEventUseCase
                                .execute(new CreateEventUseCase.Input(input.date(), input.name(), input.totalSpots(), input.partnerId()));
            return ResponseEntity.created(URI.create("/events/" + output.id())).body(output);
        } catch (ValidationException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    @PostMapping(value = "/{id}/subscribe")
    public ResponseEntity<?> subscribe(@PathVariable String id, @RequestBody SubscribeDTO input) {
        try {
            final var output = subscribeCustomerToEventUseCase.execute(new SubscribeCustomerToEventUseCase.Input(id, input.customerId()));
            return ResponseEntity.ok(output);
        } catch (ValidationException ex) {
            return ResponseEntity.unprocessableEntity().body(ex.getMessage());
        }
    }
}
