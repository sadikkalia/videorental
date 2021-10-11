package com.casumo.videorental.utils;

import com.casumo.videorental.model.Rental;
import com.casumo.videorental.model.api.request.FilmDTO;
import com.casumo.videorental.model.api.request.RentalRequest;
import com.casumo.videorental.model.api.response.RentalResponseDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestUtils {

    public static RentalRequest createDummyRentalRequest() {
        RentalRequest request = new RentalRequest();
        request.setTimestamp(System.currentTimeMillis());
        request.setCustomerId("20");
        List<FilmDTO> films = new ArrayList<>();
        FilmDTO filmDTO1 = new FilmDTO();
        filmDTO1.setFilmId("1");
        filmDTO1.setDays(3);
        films.add(filmDTO1);
        request.setFilms(films);
        return request;
    }

    public static RentalResponseDTO createDummyRentalResponse() {
        RentalResponseDTO rentalResponseDTO = new RentalResponseDTO();
        rentalResponseDTO.setRentalId("123");
        rentalResponseDTO.setTotalPrice(120);
        List<FilmDTO> films = new ArrayList<>();
        FilmDTO filmDTO1 = new FilmDTO();
        filmDTO1.setFilmId("1");
        filmDTO1.setDays(3);
        filmDTO1.setPrice(120);
        films.add(filmDTO1);
        rentalResponseDTO.setFilms(films);
        return rentalResponseDTO;
    }

    public static RentalResponseDTO createDummyRentalResponseForReturnFilms() {
        RentalResponseDTO rentalResponseDTO = new RentalResponseDTO();
        rentalResponseDTO.setTotalPrice(120);
        List<FilmDTO> films = new ArrayList<>();
        FilmDTO filmDTO1 = new FilmDTO();
        filmDTO1.setFilmId("1");
        filmDTO1.setDays(3);
        filmDTO1.setPrice(120);
        films.add(filmDTO1);
        rentalResponseDTO.setFilms(films);
        return rentalResponseDTO;
    }


    public static RentalRequest createDummyRentalRequestWithWrongFilmId() {
        RentalRequest dummyRentalRequest = createDummyRentalRequest();
        dummyRentalRequest.getFilms().get(0).setFilmId("0");
        return dummyRentalRequest;
    }

    public static RentalRequest createDummyRentalRequestWithWrongFilmRentedDays() {
        RentalRequest dummyRentalRequest = createDummyRentalRequest();
        dummyRentalRequest.getFilms().get(0).setDays(0);
        return dummyRentalRequest;
    }

    public static RentalRequest createDummyRentalRequestWithWrongCustomerId() {
        RentalRequest dummyRentalRequest = createDummyRentalRequest();
        dummyRentalRequest.setCustomerId("0");
        return dummyRentalRequest;
    }

    public static Rental createDummyRental() {
        Rental rental = new Rental();
        rental.setCustomerId("123");
        rental.setTimestamp(System.currentTimeMillis());
        Map<String, Integer> map = new HashMap<>();
        map.put("1", 3);
        rental.setFilmsDaysOfRenting(map);
        return rental;
    }
}
