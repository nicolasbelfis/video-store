package spi.repository.write;

import command.ReturnRentalCommand;
import rental.OpenedRentalAggregate;

public interface RentalRepository {
    void saveOpenedRental(OpenedRentalAggregate openedRentalAggregate);

    OpenedRentalAggregate retrieveOpenedRental(ReturnRentalCommand returnRentalCommand);

    void deleteRental(OpenedRentalAggregate openedRentalAggregate);
}
