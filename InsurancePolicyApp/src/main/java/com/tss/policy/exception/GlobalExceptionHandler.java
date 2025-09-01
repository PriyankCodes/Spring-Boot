package com.tss.policy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tss.policy.error.ResponseError;

public class GlobalExceptionHandler {
	
	@ExceptionHandler(PolicyApiException.class)
    public ResponseEntity<ResponseError> handlePolicyException(PolicyApiException ex) {
        ResponseError error = new ResponseError(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
