package com.casumo.videorental.repository;

import com.casumo.videorental.exception.IllegalParamException;
import com.casumo.videorental.exception.NotFoundException;
import com.casumo.videorental.model.Customer;
import com.casumo.videorental.model.Film;
import com.casumo.videorental.model.FilmType;
import com.casumo.videorental.model.Rental;
import com.casumo.videorental.model.api.request.FilmDTO;
import com.casumo.videorental.utils.DatabaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class RentalRepositoryImpl implements RentalRepository {

    private final Map<String, Film> films;
    private final Map<String, Customer> customers;
    private final Map<String, Rental> rentals;

    public RentalRepositoryImpl() {
        this.films = new ConcurrentHashMap<>();
        this.customers = new ConcurrentHashMap<>();
        this.rentals = new ConcurrentHashMap<>();
    }

    @Override
    public Rental getRental(String rentalId) {
        if (!this.rentals.containsKey(rentalId)) {
            throw new NotFoundException("Rental not found!");
        }

        return rentals.get(rentalId);
    }

    @Override
    public List<Rental> getAllRentals() {
        return (List<Rental>) rentals.values();
    }

    @Override
    public String saveRental(String customerId, Long timestamp, List<FilmDTO> films) {
        if (!this.customers.containsKey(customerId)) {
            throw new NotFoundException("Customer not found!");
        }

        Rental rental = new Rental();
        rental.setCustomerId(customerId);
        rental.setTimestamp(timestamp);
        rental.setFilmsDaysOfRenting(new HashMap<>());

        for (FilmDTO filmRequest : films) {
            if (!this.films.containsKey(filmRequest.getFilmId())) {
                throw new NotFoundException("Film not found!");
            }

            if (filmRequest.getDays() < 1) {
                throw new IllegalParamException("Number of days rented less than 1!");
            }

            rental.getFilmsDaysOfRenting().put(filmRequest.getFilmId(), filmRequest.getDays());
        }

        String rentalId = UUID.randomUUID().toString();
        rentals.put(rentalId, rental);

        log.info("saveRental() out rentalId={}", rentalId);

        return rentalId;
    }

    @Override
    public Film getFilm(String filmId) {
        if (!this.films.containsKey(filmId)) {
            throw new NotFoundException("Film not found!");
        }

        return this.films.get(filmId);
    }

    @PostConstruct
    public void initDatabase() {
        Film film = DatabaseUtil.createFilm("Spring Microservices in Action", FilmType.NEW);
        String filmId = "1"; //UUID.randomUUID().toString();
        this.films.put(filmId, film);
        log.info("Film id={}, name={}, type={}", filmId, film.getName(), film.getFilmType());

        film = DatabaseUtil.createFilm("Designing Data-Intensive Applications", FilmType.REGURAL);
        filmId = "2"; //UUID.randomUUID().toString();
        this.films.put(filmId, film);
        log.info("Film id={}, name={}, type={}", filmId, film.getName(), film.getFilmType());

        film = DatabaseUtil.createFilm("Effective Java", FilmType.OLD);
        filmId = "3"; //UUID.randomUUID().toString();
        this.films.put(filmId, film);
        log.info("Film id={}, name={}, type={}", filmId, film.getName(), film.getFilmType());

        Customer customer = DatabaseUtil.createCustomer("John");
        String customerId = "4"; //UUID.randomUUID().toString();
        this.customers.put(customerId, customer);
        log.info("Customer id={}, name={}", customers, customer.getName());

        customer = DatabaseUtil.createCustomer("Steve");
        customerId = "5";
        this.customers.put(customerId, customer);
        log.info("Customer id={}, name={}", customers, customer.getName());
    }
}
