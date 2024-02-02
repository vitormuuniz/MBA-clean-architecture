package br.com.fullcycle.infrastructure.rest.presenters;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import br.com.fullcycle.application.Presenter;
import br.com.fullcycle.application.customer.GetCustomerByIdUseCase;

public class GetCustomerByIdResponseEntity implements Presenter<Optional<GetCustomerByIdUseCase.Output>, Object> {

    private static final Logger LOG = LoggerFactory.getLogger(GetCustomerByIdResponseEntity.class);

    @Override
    public ResponseEntity<?> present(final Optional<GetCustomerByIdUseCase.Output> output) {
        return output
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @Override
    public ResponseEntity<?> present(Throwable throwable) {
        LOG.error("An error was observed at GetCustomerByIdUseCase", throwable);
        return ResponseEntity.notFound().build();
    }
}
