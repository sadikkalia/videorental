package com.casumo.videorental.service.rental;

import com.casumo.videorental.exception.NotFoundException;
import com.casumo.videorental.model.Rental;
import com.casumo.videorental.model.api.request.FilmDTO;
import com.casumo.videorental.model.api.request.RentalRequest;
import com.casumo.videorental.model.api.response.RentalResponseDTO;
import com.casumo.videorental.repository.RentalRepository;
import com.casumo.videorental.service.billing.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    private BillingService billingService;

    @Autowired
    private RentalRepository rentalRepository;

    @Override
    public RentalResponseDTO rentFilms(RentalRequest rentalRequest) {
        String rentalId = rentalRepository.saveRental(rentalRequest.getCustomerId(),
                rentalRequest.getTimestamp(), rentalRequest.getFilms());

        RentalResponseDTO rentalResponseDTO = billingService.calculateRentalPrice(rentalRequest.getFilms());
        rentalResponseDTO.setRentalId(rentalId);
        return rentalResponseDTO;
    }

    @Override
    public RentalResponseDTO returnFilms(RentalRequest returnRequest) {
        Rental rental = rentalRepository.getRental(returnRequest.getRentalId());

        Long timestampRented = rental.getTimestamp();
        long days = calculateNumberOfDaysDifference(timestampRented, returnRequest.getTimestamp());

        for (FilmDTO film : returnRequest.getFilms()) {
            if (!rental.getFilmsDaysOfRenting().containsKey(film.getFilmId())) {
                throw new NotFoundException("Film not found in rental order!");
            }

            Integer daysRented = rental.getFilmsDaysOfRenting().get(film.getFilmId());

            if (days > daysRented) {
                film.setDays(Math.toIntExact(days - daysRented));
            } else {
                film.setDays(0);
            }
        }

        return billingService.calculateSurcharges(returnRequest.getFilms());
    }

    private long calculateNumberOfDaysDifference(Long timestampRented, Long timestampReturned) {
        LocalDateTime rentedTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampRented),
                        TimeZone.getDefault().toZoneId());
        LocalDateTime returnTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampReturned),
                TimeZone.getDefault().toZoneId());

        return ChronoUnit.DAYS.between(rentedTime, returnTime);
    }
}
