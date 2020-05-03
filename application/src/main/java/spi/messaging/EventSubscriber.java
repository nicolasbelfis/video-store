package spi.messaging;

import reactor.core.publisher.Mono;
import spi.messaging.event.*;

public interface EventSubscriber {

    String getName();

    Mono<Void> consume(RentalCreatedEvent rentalCreatedEvent);

    Mono<Void> consume(InventoryDecrementedEvent inventoryDecrementedEvent);

    Mono<Void> consume(CustomerBonusAddedEvent customerBonusAddedEvent);

    Mono<Void> consume(RentalClosedEvent rentalClosedEvent);

    Mono<Void> consume(InventoryIncrementedEvent inventoryIncrementedEvent);
}
