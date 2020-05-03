package spi.repository.query;

import dto.AvailableFilmDTO;
import spi.messaging.event.InventoryDecrementedEvent;
import spi.messaging.event.InventoryIncrementedEvent;

import java.util.List;
import java.util.Optional;

public interface AvailableFilmViewRepository {
    List<AvailableFilmDTO> getAll();

    void updateView(InventoryDecrementedEvent s);

    Optional<AvailableFilmDTO> findById(String filmId);

    void updateView(InventoryIncrementedEvent inventoryIncrementedEvent);
}
