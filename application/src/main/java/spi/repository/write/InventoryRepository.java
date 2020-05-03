package spi.repository.write;

import inventory.InventoryAggregate;

import java.util.Optional;

public interface InventoryRepository {
    Optional<InventoryAggregate> findNonEmptyInventory(String filmId);

    void update(InventoryAggregate inventoryAggregate);

    Optional<InventoryAggregate> findBy(String filmId);
}
