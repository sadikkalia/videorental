package com.casumo.videorental.repository;

import com.casumo.videorental.exception.IllegalParamException;
import com.casumo.videorental.exception.NotFoundException;
import com.casumo.videorental.model.Film;
import com.casumo.videorental.model.FilmType;
import com.casumo.videorental.model.Rental;
import com.casumo.videorental.model.api.request.RentalRequest;
import com.casumo.videorental.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class RentalRepositoryTest {

    @Autowired
    private RentalRepositoryImpl rentalRepository;

    @AfterEach
    public void cleanUp() throws IOException {
        rentalRepository.initDatabase();
    }

    @Test
    void saveRentalShouldReturnRentalIdTest() {
        RentalRequest rentalRequest = TestUtils.createDummyRentalRequest();

        String rentalId = rentalRepository.saveRental("4", System.currentTimeMillis(), rentalRequest.getFilms());

        assertNotNull(rentalId);
    }

    @Test
    void getRentalShouldReturnCorrectRentalTest() {
        RentalRequest rentalRequest = TestUtils.createDummyRentalRequest();

        String rentalId = rentalRepository.saveRental("4", System.currentTimeMillis(), rentalRequest.getFilms());
        Rental rental = rentalRepository.getRental(rentalId);
        assertNotNull(rental);
        assertNotNull(rental.getFilmsDaysOfRenting());
        assertEquals("4", rental.getCustomerId());
        assertEquals(3, rental.getFilmsDaysOfRenting().get("1"));
    }

    @Test
    void getFilmShouldReturnFilmEntityTest() {
        Film film = rentalRepository.getFilm("1");
        assertEquals(FilmType.NEW, film.getFilmType());
        assertEquals("Spring Microservices in Action", film.getName());

    }

    @Test
    void saveRentalShouldThrowExceptionInWrongCustomerIdTest() {
        RentalRequest rentalRequest = TestUtils.createDummyRentalRequest();

        assertThrows(NotFoundException.class, () -> rentalRepository.saveRental("7", System.currentTimeMillis(), rentalRequest.getFilms()));
    }

    @Test
    void saveRentalShouldThrowExceptionInWrongFilmIdTest() {
        RentalRequest rentalRequest = TestUtils.createDummyRentalRequest();
        rentalRequest.getFilms().get(0).setFilmId("9");
        assertThrows(NotFoundException.class, () -> rentalRepository.saveRental("4", System.currentTimeMillis(), rentalRequest.getFilms()));
    }

    @Test
    void saveRentalShouldThrowExceptionInWrongNumberOfDaysTest() {
        RentalRequest rentalRequest = TestUtils.createDummyRentalRequest();
        rentalRequest.getFilms().get(0).setDays(0);
        assertThrows(IllegalParamException.class, () -> rentalRepository.saveRental("4", System.currentTimeMillis(), rentalRequest.getFilms()));
    }

    @Test
    void getFilmlShouldThrowExceptionInWrongFilmIdTest() {
        assertThrows(NotFoundException.class, () -> rentalRepository.getFilm("9"));
    }
}

