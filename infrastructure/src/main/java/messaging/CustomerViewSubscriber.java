package messaging;

import reactor.core.publisher.Mono;
import spi.messaging.EventSubscriber;
import spi.messaging.event.*;
import spi.repository.query.CustomerViewRepository;

public class CustomerViewSubscriber implements EventSubscriber {
    private final CustomerViewRepository customerViewRepository;

    public CustomerViewSubscriber(CustomerViewRepository customerViewRepository) {
        this.customerViewRepository = customerViewRepository;
    }

    public String getName() {
        return "customer subscripber";
    }

    @Override
    public Mono<Void> consume(RentalCreatedEvent rentalCreatedEvent) {
        try {
            //simulate network latency
            Thread.sleep(100);
            customerViewRepository.updateView(rentalCreatedEvent);
            System.out.println("customerView updated with rental");
        } catch (RuntimeException | InterruptedException e) {
            System.err.println("could not update view");
        }
        return Mono.empty();
    }

    @Override
    public Mono<Void> consume(InventoryDecrementedEvent inventoryDecrementedEvent) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> consume(CustomerBonusAddedEvent customerBonusAddedEvent) {
        try {
            //simulate network latency
            Thread.sleep(100);
            customerViewRepository.updateView(customerBonusAddedEvent);
            System.out.println("customerView updated with bonus");
        } catch (RuntimeException | InterruptedException e) {
            System.err.println("could not update view");
        }
        return Mono.empty();
    }

    @Override
    public Mono<Void> consume(RentalClosedEvent rentalClosedEvent) {
        try {
            //simulate network latency
            Thread.sleep(100);
            customerViewRepository.updateView(rentalClosedEvent);
            System.out.println("customerView updated with closed rental");
        } catch (RuntimeException | InterruptedException e) {
            System.err.println("could not update view");
        }
        return Mono.empty();
    }

    @Override
    public Mono<Void> consume(InventoryIncrementedEvent inventoryIncrementedEvent) {
        return Mono.empty();
    }
}
