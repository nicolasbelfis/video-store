package application;

import application.exceptions.NoInventoryFound;
import command.CreateRentalCommand;
import command.ReturnRentalCommand;
import dto.InventoryDTO;
import inventory.InventoryAggregate;
import spi.messaging.EventDispatcher;
import spi.messaging.event.Event;
import spi.messaging.event.InventoryDecrementedEvent;
import spi.messaging.event.InventoryIncrementedEvent;
import spi.repository.write.InventoryRepository;

public class InventoryCommandHandler {
    private final InventoryRepository inventoryRepository;
    private final EventDispatcher<Event> eventDispatcher;

    public InventoryCommandHandler(InventoryRepository inventoryRepository, EventDispatcher<Event> eventDispatcher) {
        this.inventoryRepository = inventoryRepository;
        this.eventDispatcher = eventDispatcher;
    }

    public void handle(CreateRentalCommand createRentalCommand) {
        InventoryAggregate inventoryAggregate = inventoryRepository
                .findNonEmptyInventory(createRentalCommand.getFilmId())
                .map(InventoryAggregate::decrement)
                .orElseThrow(NoInventoryFound::new);

        inventoryRepository.update(inventoryAggregate);

        eventDispatcher.notify(new InventoryDecrementedEvent(new InventoryDTO(inventoryAggregate.getFilmId(), inventoryAggregate.getStock())));
    }

    public void handle(ReturnRentalCommand returnRentalCommand) {
        InventoryAggregate inventoryAggregate = inventoryRepository.findBy(returnRentalCommand.getFilmId())
                .orElseThrow(NoInventoryFound::new);
        inventoryAggregate.increment();
        inventoryRepository.update(inventoryAggregate);

        eventDispatcher.notify(new InventoryIncrementedEvent(new InventoryDTO(inventoryAggregate.getFilmId(), inventoryAggregate.getStock())));
    }
}
