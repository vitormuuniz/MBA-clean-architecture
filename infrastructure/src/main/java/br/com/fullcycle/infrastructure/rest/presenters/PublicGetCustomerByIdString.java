package br.com.fullcycle.infrastructure.rest.presenters;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.fullcycle.application.Presenter;
import br.com.fullcycle.application.customer.GetCustomerByIdUseCase;

public class PublicGetCustomerByIdString implements Presenter<Optional<GetCustomerByIdUseCase.Output>, Object> {

    private static final Logger LOG = LoggerFactory.getLogger(PublicGetCustomerByIdString.class);

    @Override
    public String present(final Optional<GetCustomerByIdUseCase.Output> output) {
        return output
                .map(GetCustomerByIdUseCase.Output::id)
                .orElse("not found");
    }

    @Override
    public String present(Throwable throwable) {
        LOG.error("An error was observed at GetCustomerByIdUseCase", throwable);
        return "not found";
    }
}
