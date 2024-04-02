package org.fdifrison.webflux101.exceptionhandler;

import org.fdifrison.webflux101.dto.InputFailedValidationResponse;
import org.fdifrison.webflux101.exception.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InputValidationHandler {

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputFailedValidationResponse> handleException(InputValidationException e) {
        var response = new InputFailedValidationResponse(e.getInput(), e.getErrorCode(), e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

}
