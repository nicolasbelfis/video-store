package repository;

import application.exceptions.NoRentalFound;
import command.ReturnRentalCommand;
import org.springframework.stereotype.Component;
import rental.OpenedRentalAggregate;
import spi.repository.write.RentalRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class RentalRepositoryImpl implements RentalRepository {
    private List<OpenedRentalAggregate> rent = new ArrayList<>();

    @Override
    public void saveOpenedRental(OpenedRentalAggregate openedRentalAggregate) {
        this.rent.add(openedRentalAggregate);
    }

    @Override
    public OpenedRentalAggregate retrieveOpenedRental(ReturnRentalCommand returnRentalCommand) {
        return rent.stream().filter(openedRentalAggregate ->
                openedRentalAggregate.getCustomerId().equals(returnRentalCommand.getCustomerId())
                        && openedRentalAggregate.getFilmId().equals(returnRentalCommand.getFilmId()))
                .findFirst()
                .orElseThrow(NoRentalFound::new);
    }

    @Override
    public void deleteRental(OpenedRentalAggregate openedRentalAggregate) {
        rent.remove(openedRentalAggregate);
    }
}
