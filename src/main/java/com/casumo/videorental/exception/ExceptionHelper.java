package com.casumo.videorental.exception;

import com.casumo.videorental.model.api.response.ErrorMessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHelper {

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<ErrorMessageResponse> handleInvalidInputException(Exception ex) {
        log.error("Exception: "+ ex.getMessage());
        return new ResponseEntity<>(new ErrorMessageResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
