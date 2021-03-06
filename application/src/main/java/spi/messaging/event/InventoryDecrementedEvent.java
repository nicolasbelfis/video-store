package spi.messaging.event;

import dto.InventoryDTO;
import reactor.core.publisher.Mono;
import spi.messaging.EventSubscriber;

public class InventoryDecrementedEvent implements Event {
    private final InventoryDTO inventoryDTO;

    public InventoryDecrementedEvent(InventoryDTO inventoryDTO) {
        this.inventoryDTO = inventoryDTO;
    }

    @Override
    public Mono<Void> accept(EventSubscriber eventSubscriber) {
        return eventSubscriber.consume(this);
    }

    public InventoryDTO getInventoryDTO() {
        return inventoryDTO;
    }
}
