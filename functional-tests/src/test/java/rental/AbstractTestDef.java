package rental;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import controller.requestdto.CreateRentalCommandDTO;
import controller.requestdto.ReturnRentalCommandDTO;
import dto.AvailableFilmDTO;
import dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class AbstractTestDef {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;


    protected List<AvailableFilmDTO> callAvailableFilms() throws URISyntaxException {
        return testRestTemplate.exchange(
                RequestEntity
                        .get(new URI("http://localhost:" + port + "/video-store/available-films"))
                        .build(),
                new ParameterizedTypeReference<List<AvailableFilmDTO>>() {
                }
        ).getBody();
    }

    protected CustomerDTO getCustomer(String customerId) throws URISyntaxException {
        return testRestTemplate.exchange(
                RequestEntity
                        .get(new URI("http://localhost:" + port + "/video-store/customer/" + customerId))
                        .build(),
                CustomerDTO.class
        ).getBody();
    }

    protected ResponseEntity<String> createRental(CreateRentalCommandDTO createRentalCommandDTO) throws URISyntaxException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return testRestTemplate.exchange(
                RequestEntity
                        .post(new URI("http://localhost:" + port + "/video-store/create-rental"))
                        .headers(headers)
                        .body(objectMapper.writeValueAsString(createRentalCommandDTO))
                , String.class);
    }

    protected ResponseEntity<String> closeRental(ReturnRentalCommandDTO returnRentalCommandDTO) throws JsonProcessingException, URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return testRestTemplate.exchange(
                RequestEntity
                        .post(new URI("http://localhost:" + port + "/video-store/return-rental"))
                        .headers(headers)
                        .body(objectMapper.writeValueAsString(returnRentalCommandDTO))
                , String.class);
    }
}
