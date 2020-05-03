package spi.messaging;

public interface EventDispatcher<I> {

    void notify(I event);
}
