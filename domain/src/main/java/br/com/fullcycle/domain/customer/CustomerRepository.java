package br.com.fullcycle.domain.customer;


import java.util.Optional;

import br.com.fullcycle.domain.person.Cpf;
import br.com.fullcycle.domain.person.Email;

public interface CustomerRepository {

    Optional<Customer> customerOfId(CustomerId customerId);
    Optional<Customer> customerOfCPF(Cpf cpf);
    Optional<Customer> customerOfEmail(Email email);
    Customer create(Customer customer);
    Customer update(Customer customer);
    void deleteAll();
}
