package messaging;

import reactor.core.publisher.Mono;
import spi.messaging.EventSubscriber;
import spi.messaging.event.*;
import spi.repository.query.AvailableFilmViewRepository;

public class FilmViewSubscriber implements EventSubscriber {

    private final AvailableFilmViewRepository filmRepository;

    public FilmViewSubscriber(AvailableFilmViewRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public String getName() {
        return "film view subscriber";
    }

    @Override
    public Mono<Void> consume(RentalCreatedEvent rentalCreatedEvent) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> consume(InventoryDecrementedEvent inventoryDecrementedEvent) {
        try {
            //simulate network latency
            Thread.sleep(100);
            filmRepository.updateView(inventoryDecrementedEvent);
            System.out.println("films view updated - inventory decremented");
        } catch (RuntimeException | InterruptedException e) {
            System.err.println("could not update view " + this.getName());
        }
        return Mono.empty();
    }

    @Override
    public Mono<Void> consume(CustomerBonusAddedEvent customerBonusAddedEvent) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> consume(RentalClosedEvent rentalClosedEvent) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> consume(InventoryIncrementedEvent inventoryIncrementedEvent) {

        try {
            //simulate network latency
            Thread.sleep(100);
            filmRepository.updateView(inventoryIncrementedEvent);
            System.out.println("films view updated - inventory incremented");
        } catch (RuntimeException | InterruptedException e) {
            System.err.println("could not update view");
        }
        return Mono.empty();
    }
}
