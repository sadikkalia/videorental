package com.casumo.videorental.integration;

import com.casumo.videorental.model.api.request.RentalRequest;
import com.casumo.videorental.repository.RentalRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static com.casumo.videorental.utils.TestUtils.*;
import static com.casumo.videorental.utils.TestUtils.createDummyRentalRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FilmRentalControllerIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RentalRepositoryImpl rentalRepository;

    private String rentalId;

    @BeforeEach
    public void cleanUp() throws IOException {
        rentalRepository.initDatabase();
        RentalRequest rentalRequest = createDummyRentalRequest();
        rentalId = rentalRepository.saveRental("4", System.currentTimeMillis(), rentalRequest.getFilms());
    }

    @Test
    void rentFilmsShouldReturnOKStatusWithJSONPayloadTest() throws Exception {
        RentalRequest rentalRequest = createDummyRentalRequest();
        rentalRequest.setCustomerId("4");

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:"
                + port + "/api/v1/rent", rentalRequest, String.class);

        assertNotNull( responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    void returnFilmsShouldReturnOKStatusWithJSONPayloadForExtraDaysTest() throws Exception {
        RentalRequest rentalRequest = createDummyRentalRequest();
        rentalRequest.setRentalId(rentalId);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:"
                + port + "/api/v1/return", rentalRequest, String.class);

        assertEquals( "{\"totalPrice\":0,\"films\":[{\"filmId\":\"1\",\"days\":0,\"price\":0}]}", responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
