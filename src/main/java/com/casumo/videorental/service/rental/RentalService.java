package com.casumo.videorental.service.rental;

import com.casumo.videorental.model.api.request.RentalRequest;
import com.casumo.videorental.model.api.response.RentalResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface RentalService {
    RentalResponseDTO rentFilms(RentalRequest orderRequest);
    RentalResponseDTO returnFilms(RentalRequest body);
}
