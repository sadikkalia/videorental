package com.casumo.videorental.controller;

import com.casumo.videorental.api.v1.FilmRentalController;
import com.casumo.videorental.exception.NotFoundException;
import com.casumo.videorental.model.api.response.ErrorMessageResponse;
import com.casumo.videorental.service.rental.RentalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.casumo.videorental.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FilmRentalController.class)
public class FilmRentalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentalService rentalService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void rentFilmsShouldReturnOKStatusWithJSONPayloadTest() throws Exception {
        when(rentalService.rentFilms(any())).thenReturn(createDummyRentalResponse());

        mockMvc.perform(post("/api/v1/rent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDummyRentalRequest())))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"totalPrice\":120,\"rentalId\":\"123\",\"films\":[{\"filmId\":\"1\",\"days\":3,\"price\":120}]}"));
    }

    @Test
    void returnFilmsShouldReturnOKStatusWithJSONPayloadForExtraDaysTest() throws Exception {
        when(rentalService.returnFilms(any())).thenReturn(createDummyRentalResponseForReturnFilms());

        mockMvc.perform(post("/api/v1/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDummyRentalRequest())))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"totalPrice\":120,\"films\":[{\"filmId\":\"1\",\"days\":3,\"price\":120}]}"));
    }

    @Test
    void rentFilmsShouldThrowExceptionInFilmWrongIdTest() throws Exception {
        when(rentalService.rentFilms(any())).thenThrow(new NotFoundException("Film not found!"));

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/rent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDummyRentalRequestWithWrongFilmId())))
                .andExpect(status().isBadRequest())
                .andReturn();

        ErrorMessageResponse errorMessage = new ErrorMessageResponse("Film not found!");
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(errorMessage);

        assertThat(actualResponseBody)
                .isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void rentFilmsShouldThrowExceptionInWrongCutomerIdTest() throws Exception {
        when(rentalService.rentFilms(any())).thenThrow(new NotFoundException("Customer not found!"));

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/rent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDummyRentalRequestWithWrongCustomerId())))
                .andExpect(status().isBadRequest())
                .andReturn();

        ErrorMessageResponse errorMessage = new ErrorMessageResponse("Customer not found!");
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(errorMessage);

        assertThat(actualResponseBody)
                .isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void rentFilmsShouldThrowExceptionInWrongFilmDaysRentedTest() throws Exception {
        when(rentalService.rentFilms(any())).thenThrow(new NotFoundException("Number of days rented less than 1!"));

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/rent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDummyRentalRequestWithWrongFilmRentedDays())))
                .andExpect(status().isBadRequest())
                .andReturn();

        ErrorMessageResponse errorMessage = new ErrorMessageResponse("Number of days rented less than 1!");
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(errorMessage);

        assertThat(actualResponseBody)
                .isEqualToIgnoringWhitespace(expectedResponseBody);
    }
}
