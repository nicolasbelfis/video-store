package app;

import dto.AvailableFilmDTO;
import dto.CustomerDTO;
import dto.InventoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import repository.AvailableFilmViewRepository;
import repository.CustomerRepositoryImpl;
import repository.CustomerViewRepositoryImpl;
import repository.InventoryRepositoryImpl;
import spi.repository.write.CustomerRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SpringBootApp {

    @Autowired
    private CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class, args);

    }

    @Bean
    public CommandLineRunner demo(
            CustomerRepositoryImpl repository,
            AvailableFilmViewRepository availableFilmView,
            InventoryRepositoryImpl inventoryRepository,
            CustomerViewRepositoryImpl customerViewRepositoryImpl
    ) {
        return (args) -> {
            // save a few customers
            CustomerRepositoryImpl.CustomerEntity cust1 = new CustomerRepositoryImpl.CustomerEntity("1", BigInteger.ZERO);
            CustomerRepositoryImpl.CustomerEntity cust2 = new CustomerRepositoryImpl.CustomerEntity("2", BigInteger.ZERO);
            repository.setCustomerList(Arrays.asList(cust1, cust2));

            //save a few movies in film view and inventory
            AvailableFilmDTO availableFilmDTO = new AvailableFilmDTO("f1", "RAMBO", "OLD", 23);
            AvailableFilmDTO availableFilmDTO1 = new AvailableFilmDTO("f2", "THE JOKER", "REGULAR", 12);
            AvailableFilmDTO availableFilmDTO2 = new AvailableFilmDTO("f3", "SONIC", "NEW_RELEASE", 12);
            availableFilmView.setList(Arrays.asList(availableFilmDTO, availableFilmDTO1, availableFilmDTO2));

            InventoryDTO inv1 = new InventoryDTO("f1", BigInteger.valueOf(23));
            InventoryDTO inv2 = new InventoryDTO("f2", BigInteger.valueOf(12));
            InventoryDTO inv3 = new InventoryDTO("f3", BigInteger.valueOf(12));
            inventoryRepository.setInventory(Arrays.asList(inv1, inv2, inv3));

            List<CustomerDTO> customerDTOS = Arrays.asList(
                    new CustomerDTO(
                            "1",
                            "Amandine",
                            "PREMIUM",
                            new ArrayList<>(),
                            0),
                    new CustomerDTO(
                            "2",
                            "Nico",
                            "BASIC",
                            new ArrayList<>(),
                            0)
            );
            customerViewRepositoryImpl.setCustomerDTOList(customerDTOS);
        };
    }
}
