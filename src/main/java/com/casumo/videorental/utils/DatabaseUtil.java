package com.casumo.videorental.utils;

import com.casumo.videorental.model.Customer;
import com.casumo.videorental.model.Film;
import com.casumo.videorental.model.FilmType;

public class DatabaseUtil {

    private DatabaseUtil() {

    }

    public static Film createFilm(String name, FilmType filmType) {
        return new Film(name, filmType);
    }

    public static Customer createCustomer(String name) {
        return new Customer(name);
    }
}
