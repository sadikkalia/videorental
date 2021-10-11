package com.casumo.videorental.model.api.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class FilmDTO {

    private String filmId;
    private int days;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int price;
}
