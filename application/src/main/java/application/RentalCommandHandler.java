package application;

import command.CreateRentalCommand;
import command.ReturnRentalCommand;
import dto.RentalDTO;
import rental.ClosedRentalAggregate;
import rental.OpenedRentalAggregate;
import spi.messaging.EventDispatcher;
import spi.messaging.event.Event;
import spi.messaging.event.RentalClosedEvent;
import spi.messaging.event.RentalCreatedEvent;
import spi.repository.write.RentalRepository;

public class RentalCommandHandler {
    private final RentalRepository rentalRepository;
    final private EventDispatcher<Event> eventDispatcher;

    public RentalCommandHandler(RentalRepository rentalRepository, EventDispatcher<Event> eventDispatcher) {
        this.rentalRepository = rentalRepository;
        this.eventDispatcher = eventDispatcher;
    }

    public void handle(CreateRentalCommand createRentalCommand) {

        OpenedRentalAggregate openedRentalAggregate = OpenedRentalAggregate.builder()
                .withNumberOfDays(createRentalCommand.getNumberOfDays())
                .withInitialDate(createRentalCommand.getInitalDate())
                .withCustomerId(createRentalCommand.getCustomerId())
                .withFilmId(createRentalCommand.getFilmId())
                .withPriceRules(createRentalCommand.getFilmType(), createRentalCommand.getSubscriptionType())
                .build();

        rentalRepository.saveOpenedRental(openedRentalAggregate);
        eventDispatcher.notify(new RentalCreatedEvent(new RentalDTO(
                openedRentalAggregate.getFilmId(),
                openedRentalAggregate.getCustomerId(),
                openedRentalAggregate.calculatePrice(),
                createRentalCommand.getInitalDate(),
                createRentalCommand.getNumberOfDays())));

    }

    public void handle(ReturnRentalCommand returnRentalCommand) {
        OpenedRentalAggregate openedRentalAggregate = rentalRepository.retrieveOpenedRental(returnRentalCommand);
        ClosedRentalAggregate closedRentalAggregate = openedRentalAggregate.closeRental(returnRentalCommand.getCloseDate());
        rentalRepository.deleteRental(openedRentalAggregate);
        eventDispatcher.notify(new RentalClosedEvent(
                closedRentalAggregate.getCustomerId(),
                closedRentalAggregate.getFilmId(),
                closedRentalAggregate.getReturnDate(),
                closedRentalAggregate.calculatePrice().subtract(openedRentalAggregate.calculatePrice()),
                closedRentalAggregate.getNumberOfDays()
        ));
    }
}