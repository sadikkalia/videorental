package com.casumo.videorental.service.billing;

import com.casumo.videorental.model.api.request.FilmDTO;
import com.casumo.videorental.model.api.response.RentalResponseDTO;

import java.util.List;

public interface BillingService {
    RentalResponseDTO calculateRentalPrice(List<FilmDTO> films);

    RentalResponseDTO calculateSurcharges(List<FilmDTO> films);
}
