package com.casumo.videorental.model;

public enum PriceType {
    PREMIUM(40), BASIC(30);

    private final int value;

    PriceType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
