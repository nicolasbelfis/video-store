package spi.messaging.event;

import reactor.core.publisher.Mono;
import spi.messaging.EventSubscriber;

public interface Event {

    Mono<Void> accept(EventSubscriber eventSubscriber);
}
