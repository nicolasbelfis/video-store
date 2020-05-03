package messaging;

import spi.messaging.EventSubscriber;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import spi.messaging.EventDispatcher;
import spi.messaging.event.Event;

import java.util.List;

public class EventDispatcherInMem implements EventDispatcher<Event> {

    private final ConnectableFlux<Event> flux;
    private Listener listener;

    public EventDispatcherInMem(List<EventSubscriber> eventSubscriber) {
        this.flux = Flux.<Event>create(fluxSink -> {
            this.listener = new Listener() {

                public void onEvent(Event event) {
                    fluxSink.next(event);
                }

                public void processComplete() {
                    fluxSink.complete();
                }
            };
        }).publish();
        eventSubscriber.forEach(this::registerSubscriber);
        flux.connect();
    }

    private void registerSubscriber(EventSubscriber eventSubscriber) {
        flux.publishOn(Schedulers.single())
                .flatMap(event -> event.accept(eventSubscriber))
                .doOnError(Throwable::printStackTrace)
                .subscribe(
                        aVoid -> System.out.println("spi.messaging.event received by " + eventSubscriber.getName()),
                        Throwable::printStackTrace
                );
    }

    public void notify(Event message) {
        listener.onEvent(message);
    }


    private interface Listener {
        void onEvent(Event event);

        void processComplete();
    }
}
