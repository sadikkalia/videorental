package com.casumo.videorental.model;

import static com.casumo.videorental.model.PriceType.BASIC;
import static com.casumo.videorental.model.PriceType.PREMIUM;

public enum FilmType {
    NEW(PREMIUM, 1, PREMIUM),
    REGURAL(BASIC, 3, BASIC),
    OLD(BASIC, 5, BASIC);

    private final PriceType firstDaysPriceType;
    private final int firstNoOfDays;
    private final PriceType eachDayPriceType;

    FilmType(PriceType firstDaysPriceType, int firstNoOfDays, PriceType eachDayPriceType) {
        this.firstDaysPriceType = firstDaysPriceType;
        this.eachDayPriceType = eachDayPriceType;
        this.firstNoOfDays = firstNoOfDays;
    }

    public int calculatePrice(int days) {
        if (days > firstNoOfDays) {
            return firstDaysPriceType.getValue() + ((days-firstNoOfDays) * eachDayPriceType.getValue());
        }
        return firstDaysPriceType.getValue();
    }

    public int calculateExtraPrice(int days) {
        return days * eachDayPriceType.getValue();
    }
}
