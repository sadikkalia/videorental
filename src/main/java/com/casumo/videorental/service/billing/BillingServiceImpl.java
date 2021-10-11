package com.casumo.videorental.service.billing;

import com.casumo.videorental.model.Film;
import com.casumo.videorental.model.api.request.FilmDTO;
import com.casumo.videorental.model.api.response.RentalResponseDTO;
import com.casumo.videorental.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillingServiceImpl implements BillingService {

    @Autowired
    private RentalRepository rentalRepository;

    @Override
    public RentalResponseDTO calculateRentalPrice(List<FilmDTO> films) {
        RentalResponseDTO rentalResponse = new RentalResponseDTO();
        int totalPrice = 0;

        for (FilmDTO item : films) {
            Film film = rentalRepository.getFilm(item.getFilmId());
            int price = film.getPriceForNoOfDays(item.getDays());
            totalPrice += price;
            item.setPrice(price);
        }

        rentalResponse.setFilms(films);
        rentalResponse.setTotalPrice(totalPrice);
        return rentalResponse;
    }

    @Override
    public RentalResponseDTO calculateSurcharges(List<FilmDTO> films) {
        RentalResponseDTO rentalResponse = new RentalResponseDTO();
        int totalPrice = 0;

        for (FilmDTO item : films) {
            Film film = rentalRepository.getFilm(item.getFilmId());

            int price = film.getPriceForExtraDays(item.getDays());
            totalPrice += price;
            item.setPrice(price);
        }
        rentalResponse.setFilms(films);
        rentalResponse.setTotalPrice(totalPrice);

        return rentalResponse;
    }
}
