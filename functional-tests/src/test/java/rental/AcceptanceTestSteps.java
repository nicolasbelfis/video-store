package rental;

import com.fasterxml.jackson.core.JsonProcessingException;
import controller.requestdto.CreateRentalCommandDTO;
import controller.requestdto.ReturnRentalCommandDTO;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dto.AvailableFilmDTO;
import dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import repository.AvailableFilmViewRepository;
import repository.CustomerRepositoryImpl;
import repository.CustomerViewRepositoryImpl;
import repository.InventoryRepositoryImpl;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


public class AcceptanceTestSteps extends AbstractTestDef {

    @Autowired
    CustomerRepositoryImpl repository;
    @Autowired
    AvailableFilmViewRepository availableFilmView;
    @Autowired
    InventoryRepositoryImpl inventoryRepository;
    @Autowired
    CustomerViewRepositoryImpl customerViewRepositoryImpl;

    @Given("^available film$")
    public void given_available_film(DataTable dataTable) throws URISyntaxException {
        //  | filmId | name | type | stock |
        List<AvailableFilmDTO> resp = callAvailableFilms();
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        assertEquals(rows.get(0).get("filmId"), resp.get(0).getFilmId());
        assertEquals(rows.get(0).get("name"), resp.get(0).getFilmName());
        assertEquals(rows.get(0).get("type"), resp.get(0).getFilmCategory());
        assertEquals(rows.get(0).get("stock"), String.valueOf(resp.get(0).getStock()));
    }

    @Given("^customer with data$")
    public void given_customer(DataTable dataTable) throws URISyntaxException {
        //   | customerID | subscription | bonus |
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        CustomerDTO resp = getCustomer(rows.get(0).get("customerID"));
        assertEquals(rows.get(0).get("customerID"), resp.getCustomerId());
        assertEquals(rows.get(0).get("subscription"), resp.getSubscriptionType());
        assertEquals(rows.get(0).get("bonus"), String.valueOf(resp.getBonus()));
    }

    @When("^create a rental with input$")
    public void when_call_create_rental(DataTable dataTable) throws URISyntaxException, JsonProcessingException {
        // | customerID | filmId | subscription | type | numberOfDays | startDate  |
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        assertTrue(inventoryRepository.findNonEmptyInventory("f1").isPresent());
        ResponseEntity<String> responseEntity = createRental(new CreateRentalCommandDTO(
                Integer.parseInt(rows.get(0).get("numberOfDays")),
                LocalDate.parse(rows.get(0).get("startDate")),
                rows.get(0).get("customerID"),
                rows.get(0).get("filmId"),
                rows.get(0).get("type"),
                rows.get(0).get("subscription")
        ));
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        assertFalse(inventoryRepository.findNonEmptyInventory("f1").isPresent());
    }

    @When("^return same rental with input$")
    public void when_call_return_rental(DataTable dataTable) throws URISyntaxException, JsonProcessingException {
        //          | customerID | filmId | returnDate |
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        ResponseEntity<String> responseEntity = closeRental(new ReturnRentalCommandDTO(
                LocalDate.parse(rows.get(0).get("returnDate")),
                rows.get(0).get("customerID"),
                rows.get(0).get("filmId")
        ));
        assertEquals(responseEntity.getStatusCode(), HttpStatus.ACCEPTED);
        assertTrue(inventoryRepository.findNonEmptyInventory("f1").isPresent());
    }


    @Then("^create same rental fail as stock in 0$")
    public void then_call_create_rental(DataTable dataTable) throws URISyntaxException, JsonProcessingException {
        // | customerID | filmId | subscription | type | numberOfDays | startDate  |
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        assertFalse(inventoryRepository.findNonEmptyInventory("f1").isPresent());
        ResponseEntity<String> responseEntity = createRental(new CreateRentalCommandDTO(
                Integer.parseInt(rows.get(0).get("numberOfDays")),
                LocalDate.parse(rows.get(0).get("startDate")),
                rows.get(0).get("customerID"),
                rows.get(0).get("filmId"),
                rows.get(0).get("type"),
                rows.get(0).get("subscription")
        ));
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Then("^available film view is eventually updated$")
    public void then_view(DataTable dataTable) throws URISyntaxException, InterruptedException {
        //  | filmId | name | type | stock |
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        int retry = 1;
        boolean asserted = false;
        while (retry <= 10 && !asserted) {
            Thread.sleep(100);
            List<AvailableFilmDTO> resp = callAvailableFilms();
            if (resp.get(0).getStock() == Integer.parseInt(rows.get(0).get("stock"))) {
                assertEquals(rows.get(0).get("filmId"), resp.get(0).getFilmId());
                assertEquals(rows.get(0).get("name"), resp.get(0).getFilmName());
                assertEquals(rows.get(0).get("type"), resp.get(0).getFilmCategory());
                asserted = true;
            }
            System.out.println("retry step");
            retry++;
        }
        assertTrue(asserted);
    }

    @Then("^customer view is eventually updated$")
    public void then_customer_view(DataTable dataTable) throws URISyntaxException, InterruptedException {
        //        | filmId | customerID | price | numberOfDays | startDate  |
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        int retry = 1;
        boolean asserted = false;
        while (retry <= 10 && !asserted) {
            Thread.sleep(100);
            CustomerDTO resp = getCustomer(rows.get(0).get("customerID"));
            if (resp.getRentalDTOList().size() != 0) {
                assertEquals(rows.get(0).get("filmId"), resp.getRentalDTOList().get(0).getFilmId());
                assertEquals(rows.get(0).get("price"), resp.getRentalDTOList().get(0).getPrice().toString());
                assertEquals(rows.get(0).get("numberOfDays"), String.valueOf(resp.getRentalDTOList().get(0).getNumberOfDays()));
                asserted = true;
            }
            System.out.println("retry step");
            retry++;
        }
        assertTrue(asserted);
    }

    @Then("^customer view is eventually updated with overcharge$")
    public void then_customer_view_after_return(DataTable dataTable) throws URISyntaxException, InterruptedException {
        //         | filmId | customerID | price | numberOfDays | returnDate | overcharge |
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        int retry = 1;
        boolean asserted = false;
        while (retry <= 10 && !asserted) {
            Thread.sleep(100);
            CustomerDTO resp = getCustomer(rows.get(0).get("customerID"));
            if (resp.getRentalDTOList().size() != 0 && resp.getRentalDTOList().get(0).getOverCharge() != null) {
                assertEquals(rows.get(0).get("filmId"), resp.getRentalDTOList().get(0).getFilmId());
                assertEquals(rows.get(0).get("price"), resp.getRentalDTOList().get(0).getPrice().toString());
                assertEquals(rows.get(0).get("overcharge"), resp.getRentalDTOList().get(0).getOverCharge().toString());
                assertEquals(rows.get(0).get("price"), resp.getRentalDTOList().get(0).getPrice().toString());
                assertEquals(rows.get(0).get("numberOfDays"), String.valueOf(resp.getRentalDTOList().get(0).getNumberOfDays()));
                asserted = true;
            }
            System.out.println("retry step");
            retry++;
        }
        assertTrue(asserted);
    }

}
