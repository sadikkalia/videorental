package com.casumo.videorental.model.api.request;

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
public class RentalRequest {

    private String customerId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String rentalId;
    private Long timestamp;
    private List<FilmDTO> films;

}
