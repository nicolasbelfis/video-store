package controller;

import api.ApplicationService;
import application.exceptions.InternalApplicationException;
import application.exceptions.UnvalidParameterException;
import controller.exceptions.WrongInputInRentalCreation;
import controller.requestdto.CreateRentalCommandDTO;
import controller.requestdto.ReturnRentalCommandDTO;
import dto.AvailableFilmDTO;
import dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spi.repository.query.AvailableFilmViewRepository;
import spi.repository.query.CustomerViewRepository;

import java.util.List;

@RestController
@RequestMapping("/video-store")
public class VideoStoreController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private AvailableFilmViewRepository availableFilmViewRepository;

    @Autowired
    private CustomerViewRepository customerViewRepository;

    @GetMapping("/available-films")
    public List<AvailableFilmDTO> findAvailableFilms() {
        System.out.println("return results");
        return availableFilmViewRepository.getAll();
    }

    @PostMapping(value = "/create-rental", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createRental(@RequestBody CreateRentalCommandDTO commandLine) {
        applicationService.createRental(commandLine.toCommand());
        System.out.println("rental created");
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(null);
    }

    @PostMapping(value = "/return-rental", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> returnRental(@RequestBody ReturnRentalCommandDTO commandLine) {
        applicationService.returnRental(commandLine.toCommand());
        System.out.println("rental returned");
        return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(null);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<CustomerDTO> findCustomer(@PathVariable String customerId) {
        return customerViewRepository.findCustomerById(customerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @ExceptionHandler(UnvalidParameterException.class)
    public ResponseEntity<String> parameterErrorHandler(UnvalidParameterException e) {
        return new ResponseEntity<>(
                e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongInputInRentalCreation.class)
    public ResponseEntity<String> parameterErrorHandler(WrongInputInRentalCreation e) {
        return new ResponseEntity<>(
                e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalApplicationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void parameterErrorHandler() {
    }
}