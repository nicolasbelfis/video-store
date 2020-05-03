package app;

import api.ApplicationService;
import application.CustomerCommandHandler;
import application.InventoryCommandHandler;
import application.RentalCommandHandler;
import application.VideoRentalApplication;
import messaging.CustomerViewSubscriber;
import messaging.EventDispatcherInMem;
import messaging.FilmViewSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import spi.messaging.EventDispatcher;
import spi.messaging.EventSubscriber;
import spi.messaging.event.Event;
import spi.repository.query.AvailableFilmViewRepository;
import spi.repository.query.CustomerViewRepository;
import spi.repository.write.CustomerRepository;
import spi.repository.write.InventoryRepository;
import spi.repository.write.RentalRepository;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@Configuration
@ComponentScan(basePackages = {"controller", "messaging", "repository"})
@EnableSwagger2
public class SpringConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public EventSubscriber eventSubscriberForInventoryView(AvailableFilmViewRepository availableFilmViewRepository) {
        return new FilmViewSubscriber(availableFilmViewRepository);
    }

    @Bean
    public EventSubscriber eventSubscriberForCustomerView(CustomerViewRepository customerViewRepository) {
        return new CustomerViewSubscriber(customerViewRepository);
    }

    @Bean
    public EventDispatcher<Event> startEventDispatcher(EventSubscriber... eventSubscribers) {

        return new EventDispatcherInMem(Arrays.asList(eventSubscribers));
    }

    @Bean
    public ApplicationService applicationService(EventDispatcher<Event> eventDispatcher, RentalRepository rentalRepo, InventoryRepository inventoryRepo, CustomerRepository customerRepository) {
        return new ApplicationService(
                new VideoRentalApplication(
                        new RentalCommandHandler(rentalRepo, eventDispatcher),
                        new InventoryCommandHandler(inventoryRepo, eventDispatcher),
                        new CustomerCommandHandler(customerRepository, eventDispatcher)));
    }
}
