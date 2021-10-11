package com.casumo.videorental.service;

import com.casumo.videorental.exception.IllegalParamException;
import com.casumo.videorental.exception.NotFoundException;
import com.casumo.videorental.model.api.request.RentalRequest;
import com.casumo.videorental.model.api.response.ErrorMessageResponse;
import com.casumo.videorental.model.api.response.RentalResponseDTO;
import com.casumo.videorental.repository.RentalRepository;
import com.casumo.videorental.service.billing.BillingService;
import com.casumo.videorental.service.rental.RentalServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static com.casumo.videorental.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RentalServiceTest {

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private BillingService billingService;

    @InjectMocks
    private RentalServiceImpl rentalService;

    @Test
    void rentFilmsShouldReturnCorrectTotalPriceAndRentalIdTest() {
        RentalRequest rentalRequest = createDummyRentalRequest();

        when(rentalRepository.saveRental(rentalRequest.getCustomerId(), System.currentTimeMillis(), rentalRequest.getFilms()))
                .thenReturn("124");
        when(billingService.calculateRentalPrice(rentalRequest.getFilms())).thenReturn(createDummyRentalResponse());

        RentalResponseDTO rentalResponse = rentalService.rentFilms(rentalRequest);
        assertEquals("124", rentalResponse.getRentalId());
        assertEquals(120, rentalResponse.getTotalPrice());
    }

    @Test
    void rentFilmsShouldReturnCorrectPriceForFirstFilmTAndFilmIdTest() {
        RentalRequest rentalRequest = createDummyRentalRequest();

        when(rentalRepository.saveRental(rentalRequest.getCustomerId(), System.currentTimeMillis(), rentalRequest.getFilms()))
                .thenReturn("124");
        when(billingService.calculateRentalPrice(rentalRequest.getFilms())).thenReturn(createDummyRentalResponse());

        RentalResponseDTO rentalResponse = rentalService.rentFilms(rentalRequest);
        assertEquals("1", rentalResponse.getFilms().get(0).getFilmId());
        assertEquals(120, rentalResponse.getFilms().get(0).getPrice());
    }

    @Test
    void returnFilmsShouldReturnCorrectTotalPriceTest() {
        RentalRequest rentalRequest = createDummyRentalRequest();

        when(rentalRepository.getRental(rentalRequest.getRentalId()))
                .thenReturn(createDummyRental());
        when(billingService.calculateSurcharges(rentalRequest.getFilms())).thenReturn(createDummyRentalResponseForReturnFilms());

        RentalResponseDTO rentalResponse = rentalService.returnFilms(rentalRequest);
        assertEquals(120, rentalResponse.getTotalPrice());
    }

    @Test
    void returnFilmsShouldReturnCorrectPriceForFirstFilmTAndFilmIdTest() {
        RentalRequest rentalRequest = createDummyRentalRequest();

        when(rentalRepository.getRental(rentalRequest.getRentalId()))
                .thenReturn(createDummyRental());
        when(billingService.calculateSurcharges(rentalRequest.getFilms())).thenReturn(createDummyRentalResponseForReturnFilms());

        RentalResponseDTO rentalResponse = rentalService.returnFilms(rentalRequest);
        assertEquals("1", rentalResponse.getFilms().get(0).getFilmId());
        assertEquals(3, rentalResponse.getFilms().get(0).getDays());
        assertEquals(120, rentalResponse.getFilms().get(0).getPrice());
    }

    @Test
    void rentFilmsShouldThrowExceptionInFilmWrongIdTest() throws Exception {
        RentalRequest rentalRequest = createDummyRentalRequest();

        when(rentalRepository.saveRental(rentalRequest.getCustomerId(), System.currentTimeMillis(), rentalRequest.getFilms()))
                .thenThrow(new NotFoundException("Film not found!"));

        assertThrows(NotFoundException.class, () -> {
            rentalService.rentFilms(rentalRequest);
        });
    }

    @Test
    void rentFilmsShouldThrowExceptionInWrongCutomerIdTest() throws Exception {
        RentalRequest rentalRequest = createDummyRentalRequest();

        when(rentalRepository.saveRental(rentalRequest.getCustomerId(), System.currentTimeMillis(), rentalRequest.getFilms()))
                .thenThrow(new NotFoundException("Customer not found!"));

        assertThrows(NotFoundException.class, () -> {
            rentalService.rentFilms(rentalRequest);
        });
    }

    @Test
    void rentFilmsShouldThrowExceptionInWrongFilmDaysRentedTest() throws Exception {
        RentalRequest rentalRequest = createDummyRentalRequest();

        when(rentalRepository.saveRental(rentalRequest.getCustomerId(), System.currentTimeMillis(), rentalRequest.getFilms()))
                .thenThrow(new IllegalParamException("Number of days rented less than 1!"));

        assertThrows(IllegalParamException.class, () -> {
            rentalService.rentFilms(rentalRequest);
        });
    }

    @Test
    void renturnFilmsShouldThrowExceptionInFilmWrongIdTest() throws Exception {
        RentalRequest rentalRequest = createDummyRentalRequest();

        when(rentalRepository.saveRental(rentalRequest.getCustomerId(), System.currentTimeMillis(), rentalRequest.getFilms()))
                .thenThrow(new NotFoundException("Film not found in rental order!"));

        assertThrows(NotFoundException.class, () -> {
            rentalService.rentFilms(rentalRequest);
        });
    }
}



