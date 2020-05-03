package repository;

import dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spi.messaging.event.CustomerBonusAddedEvent;
import spi.messaging.event.RentalClosedEvent;
import spi.messaging.event.RentalCreatedEvent;
import spi.repository.query.AvailableFilmViewRepository;
import spi.repository.query.CustomerViewRepository;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerViewRepositoryImpl implements CustomerViewRepository {

    private List<CustomerDTO> customerDTOList;

    @Override
    public Optional<CustomerDTO> findCustomerById(String customerId) {
        return customerDTOList.stream()
                .filter(customerDTO -> customerDTO.getCustomerId().equals(customerId))
                .findFirst();
    }

    @Override
    public void updateView(RentalCreatedEvent e) {
        customerDTOList.stream()
                .filter(customerDTO -> customerDTO.getCustomerId().equals(e.getRentalDTO().getCustomerId()))
                .findFirst()
                .ifPresent(customerDTO ->
                        customerDTO.getRentalDTOList().add(e.getRentalDTO())
                );
    }

    @Override
    public void updateView(CustomerBonusAddedEvent customerBonusAddedEvent) {
        customerDTOList.stream()
                .filter(customerDTO -> customerDTO.getCustomerId().equals(customerBonusAddedEvent.getCustomerId()))
                .findFirst()
                .ifPresent(customerDTO ->
                        customerDTO.setBonus(customerBonusAddedEvent.getBonus().intValue())
                );
    }

    @Override
    public void updateView(RentalClosedEvent rentalClosedEvent) {
        customerDTOList.stream()
                .filter(customerDTO -> customerDTO.getCustomerId().equals(rentalClosedEvent.getCustomerId()))
                .flatMap(customerDTO -> customerDTO.getRentalDTOList().stream())
                .filter(rentalDTO -> rentalDTO.getFilmId().equals(rentalClosedEvent.getFilmId())
                        && rentalDTO.getCustomerId().equals(rentalClosedEvent.getCustomerId()))
                .findFirst()
                .ifPresent(rentalDTO -> {

                            rentalDTO.setOverCharge(rentalClosedEvent.getOverCharge());
                            rentalDTO.setReturnDate(rentalClosedEvent.getReturnDate());
                            rentalDTO.setNumberOfDays(rentalClosedEvent.getNumberOfDays());
                        }
                );
    }

    public void setCustomerDTOList(List<CustomerDTO> customerDTOList) {
        this.customerDTOList = customerDTOList;
    }
}
