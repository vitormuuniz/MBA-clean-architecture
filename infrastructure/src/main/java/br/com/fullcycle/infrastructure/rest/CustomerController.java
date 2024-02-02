package br.com.fullcycle.infrastructure.rest;

import br.com.fullcycle.application.Presenter;
import br.com.fullcycle.application.customer.CreateCustomerUseCase;
import br.com.fullcycle.application.customer.GetCustomerByIdUseCase;
import br.com.fullcycle.domain.exceptions.ValidationException;
import br.com.fullcycle.infrastructure.dtos.NewCustomerDTO;
import br.com.fullcycle.infrastructure.rest.presenters.GetCustomerByIdResponseEntity;
import br.com.fullcycle.infrastructure.rest.presenters.PublicGetCustomerByIdString;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

//Adapter
@RestController
@RequestMapping(value = "customers")
public class CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final GetCustomerByIdUseCase getCustomerByIdUseCase;

    public CustomerController(final CreateCustomerUseCase createCustomerUseCase, final GetCustomerByIdUseCase getCustomerByIdUseCase) {
        this.createCustomerUseCase = Objects.requireNonNull(createCustomerUseCase);
        this.getCustomerByIdUseCase = Objects.requireNonNull(getCustomerByIdUseCase);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody NewCustomerDTO input) {
        try {
            final var output = createCustomerUseCase
                                .execute(new CreateCustomerUseCase.Input(input.cpf(), input.email(), input.name()));
            return ResponseEntity.created(URI.create("/customers/" + output.id())).body(output);
        } catch (ValidationException ex) {
            return ResponseEntity.unprocessableEntity().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Object get(@PathVariable String id, @RequestHeader(name = "X-Public", required = false) String xPublic) {
        Presenter<Optional<GetCustomerByIdUseCase.Output>, Object> presenter = new GetCustomerByIdResponseEntity();

        if (xPublic != null) {
            presenter = new PublicGetCustomerByIdString();
        }

        return getCustomerByIdUseCase.execute(new GetCustomerByIdUseCase.Input(id), presenter);
    }
}