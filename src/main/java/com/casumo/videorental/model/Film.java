package com.casumo.videorental.model;

import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Setter
public class Film {

    private String name;
    private FilmType filmType;

    public int getPriceForNoOfDays(int days) {
        return filmType.calculatePrice(days);
    }

    public int getPriceForExtraDays(int days) {
        return filmType.calculateExtraPrice(days);
    }
}
