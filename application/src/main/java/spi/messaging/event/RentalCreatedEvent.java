package spi.messaging.event;

import dto.RentalDTO;
import reactor.core.publisher.Mono;
import spi.messaging.EventSubscriber;

public class RentalCreatedEvent implements Event {
    private final RentalDTO rentalDTO;

    public RentalCreatedEvent(RentalDTO rentalDTO) {
        this.rentalDTO = rentalDTO;
    }

    @Override
    public Mono<Void> accept(EventSubscriber eventSubscriber) {
        return eventSubscriber.consume(this);
    }

    public RentalDTO getRentalDTO() {
        return rentalDTO;
    }
}
