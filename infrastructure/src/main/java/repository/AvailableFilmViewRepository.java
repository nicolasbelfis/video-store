package repository;

import dto.AvailableFilmDTO;
import org.springframework.stereotype.Component;
import spi.messaging.event.InventoryDecrementedEvent;
import spi.messaging.event.InventoryIncrementedEvent;

import java.util.List;
import java.util.Optional;

@Component
public class AvailableFilmViewRepository implements spi.repository.query.AvailableFilmViewRepository {

    private List<AvailableFilmDTO> list;

    @Override
    public List<AvailableFilmDTO> getAll() {
        return list;
    }

    @Override
    public void updateView(InventoryDecrementedEvent s) {
        list.stream().filter(availableFilmDTO ->
                availableFilmDTO.getFilmId().equals(s.getInventoryDTO().getFilmId())
        ).findFirst()
                .ifPresent(availableFilmDTO -> availableFilmDTO.setStock(s.getInventoryDTO().getStock().intValue()));
    }

    @Override
    public Optional<AvailableFilmDTO> findById(String filmId) {
        return list.stream().filter(availableFilmDTO -> availableFilmDTO.getFilmId().equals(filmId))
                .findFirst();
    }

    @Override
    public void updateView(InventoryIncrementedEvent inventoryIncrementedEvent) {
        list.stream().filter(availableFilmDTO ->
                availableFilmDTO.getFilmId().equals(inventoryIncrementedEvent.getInventoryDTO().getFilmId())
        ).findFirst()
                .ifPresent(availableFilmDTO -> availableFilmDTO.setStock(inventoryIncrementedEvent.getInventoryDTO().getStock().intValue()));

    }

    public void setList(List<AvailableFilmDTO> list) {
        this.list = list;
    }
}
