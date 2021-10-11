package com.casumo.videorental.model.api.response;

import com.casumo.videorental.model.api.request.FilmDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
public class RentalResponseDTO {

    private int totalPrice;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String rentalId;
    private List<FilmDTO> films;
}
