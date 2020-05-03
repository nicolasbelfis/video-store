package repository;

import dto.InventoryDTO;
import inventory.InventoryAggregate;
import org.springframework.stereotype.Component;
import spi.repository.write.InventoryRepository;

import java.util.List;
import java.util.Optional;

@Component
public class InventoryRepositoryImpl implements InventoryRepository {
    private List<InventoryDTO> inventory;

    @Override
    public Optional<InventoryAggregate> findNonEmptyInventory(String filmId) {
        return inventory.stream().filter(inventoryDTO -> inventoryDTO.getFilmId().equals(filmId))
                .filter(inventoryDTO -> inventoryDTO.getStock().intValue() > 0)
                .map(inventoryDTO -> new InventoryAggregate(filmId, inventoryDTO.getStock()))
                .findFirst();
    }

    @Override
    public void update(InventoryAggregate inventoryAggregate) {
        inventory.stream().filter(inventoryDTO -> inventoryDTO.getFilmId().equals(inventoryAggregate.getFilmId()))
                .findFirst()
                .ifPresent(inventoryDTO -> inventoryDTO.setStock(inventoryAggregate.getStock()));
    }

    @Override
    public Optional<InventoryAggregate> findBy(String filmId) {
        return inventory.stream().filter(inventoryDTO -> inventoryDTO.getFilmId().equals(filmId))
                .map(inventoryDTO -> new InventoryAggregate(filmId, inventoryDTO.getStock()))
                .findFirst();
    }

    public void setInventory(List<InventoryDTO> inventory) {
        this.inventory = inventory;
    }
}
