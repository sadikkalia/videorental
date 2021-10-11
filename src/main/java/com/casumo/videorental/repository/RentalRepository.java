package com.casumo.videorental.repository;

import com.casumo.videorental.model.Film;
import com.casumo.videorental.model.Rental;
import com.casumo.videorental.model.api.request.FilmDTO;

import java.util.List;

public interface RentalRepository {

    Rental getRental(String rentalId);

    List<Rental> getAllRentals();

    String saveRental(String customerId, Long timestamp, List<FilmDTO> films);

    Film getFilm(String filmId);
}
