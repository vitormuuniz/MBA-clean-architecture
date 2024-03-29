package br.com.fullcycle.application.customer;

import java.util.Objects;
import java.util.Optional;

import br.com.fullcycle.application.UseCase;
import br.com.fullcycle.domain.customer.CustomerId;
import br.com.fullcycle.domain.customer.CustomerRepository;

//Port
public class GetCustomerByIdUseCase extends UseCase<GetCustomerByIdUseCase.Input, Optional<GetCustomerByIdUseCase.Output>> {

    private final CustomerRepository customerRepository;

    public GetCustomerByIdUseCase(CustomerRepository customerRepository) {
        this.customerRepository = Objects.requireNonNull(customerRepository);
    }

    @Override
    public Optional<Output> execute(Input input) {
        return customerRepository.customerOfId(CustomerId.with(input.id))
                .map(customer -> new Output(
                                    customer.customerId().value(),
                                    customer.cpf().value(),
                                    customer.email().value(),
                                    customer.name().value())
                );
    }

    public record Input(String id) {}

    public record Output(String id, String cpf, String email, String name) {}
}
