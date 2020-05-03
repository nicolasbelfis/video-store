package spi.repository.write;

import customer.CustomerAggregate;

import java.util.Optional;

public interface CustomerRepository {
    Optional<CustomerAggregate> findById(String customerId);

    void update(CustomerAggregate customerAggregate);
}
