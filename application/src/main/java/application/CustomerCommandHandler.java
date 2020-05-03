package application;

import application.exceptions.NoCustomerFound;
import command.CreateRentalCommand;
import customer.CustomerAggregate;
import customer.FilmTypeBonus;
import spi.messaging.EventDispatcher;
import spi.messaging.event.CustomerBonusAddedEvent;
import spi.messaging.event.Event;
import spi.repository.write.CustomerRepository;

public class CustomerCommandHandler {
    private final CustomerRepository customerRepository;
    private final EventDispatcher<Event> eventDispatcher;

    public CustomerCommandHandler(CustomerRepository customerRepository, EventDispatcher<Event> eventDispatcher) {
        this.customerRepository = customerRepository;
        this.eventDispatcher = eventDispatcher;
    }


    public void handle(CreateRentalCommand createRentalCommand) {
        CustomerAggregate customer = customerRepository.findById(createRentalCommand.getCustomerId())
                .map(customerAggregate -> customerAggregate.
                        addBonusFromFilm(FilmTypeBonus.of(createRentalCommand.getFilmType()))
                ).orElseThrow(NoCustomerFound::new);

        customerRepository.update(customer);
        eventDispatcher.notify(new CustomerBonusAddedEvent(customer.getCustomerId(), customer.getBonus()));

    }
}
