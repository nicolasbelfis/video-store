package spi.messaging.event;

import reactor.core.publisher.Mono;
import spi.messaging.EventSubscriber;

import java.math.BigInteger;

public class CustomerBonusAddedEvent implements Event {
    public CustomerBonusAddedEvent(String customerId, BigInteger bonus) {
        this.customerId = customerId;
        this.bonus = bonus;
    }

    @Override
    public Mono<Void> accept(EventSubscriber eventSubscriber) {
        return eventSubscriber.consume(this);
    }

    public String getCustomerId() {
        return customerId;
    }

    public BigInteger getBonus() {
        return bonus;
    }

    private final String customerId;

    private final BigInteger bonus;
}
