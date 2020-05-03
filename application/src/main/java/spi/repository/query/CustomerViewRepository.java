package spi.repository.query;

import dto.CustomerDTO;
import spi.messaging.event.CustomerBonusAddedEvent;
import spi.messaging.event.RentalClosedEvent;
import spi.messaging.event.RentalCreatedEvent;

import java.util.Optional;

public interface CustomerViewRepository {

    Optional<CustomerDTO> findCustomerById(String customerId);

    void updateView(RentalCreatedEvent e);

    void updateView(CustomerBonusAddedEvent customerBonusAddedEvent);

    void updateView(RentalClosedEvent rentalClosedEvent);
}
