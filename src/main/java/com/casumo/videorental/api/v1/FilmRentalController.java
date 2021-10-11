package com.casumo.videorental.api.v1;

import com.casumo.videorental.model.api.request.RentalRequest;
import com.casumo.videorental.model.api.response.RentalResponseDTO;
import com.casumo.videorental.service.rental.RentalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class FilmRentalController {

    @Autowired
    private RentalService rentalService;

    @PostMapping(value = "/rent",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RentalResponseDTO> rentFilms(final @RequestBody RentalRequest rentalOrder) {
        log.info("rentFilms() in customerId={}", rentalOrder.getCustomerId());

        RentalResponseDTO rentalResponse = rentalService.rentFilms(rentalOrder);

        log.debug("rentFilms() out totalPrice={}", 10);
        return ResponseEntity.status(HttpStatus.OK).body(rentalResponse);
    }

    @PostMapping(value = "/return",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RentalResponseDTO> returnFilms(final @RequestBody RentalRequest rentalOrder) {
        log.info("returnFilms() in customerId={}", rentalOrder.getCustomerId());

        RentalResponseDTO rentalResponse = rentalService.returnFilms(rentalOrder);

        log.debug("returnFilms() out totalPrice={}", 10);
        return ResponseEntity.status(HttpStatus.OK).body(rentalResponse);
    }
}
