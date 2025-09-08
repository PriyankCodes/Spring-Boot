package com.tss.bank.exception;

import com.tss.bank.error.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // handle general Bank API errors
    @ExceptionHandler(BankApiException.class)
    public ResponseEntity<ResponseError> handleBankApiException(BankApiException exception) {
        ResponseError error = new ResponseError();
        error.setMessage(exception.getMessage());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // handle Customer not found errors
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ResponseError> handleCustomerNotFoundException(CustomerNotFoundException exception) {
        ResponseError error = new ResponseError();
        error.setMessage(exception.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // handle Account not found errors
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ResponseError> handleAccountNotFoundException(AccountNotFoundException exception) {
        ResponseError error = new ResponseError();
        error.setMessage(exception.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // handle User not found errors
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseError> handleUserNotFoundException(UserNotFoundException exception) {
        ResponseError error = new ResponseError();
        error.setMessage(exception.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // handle Transaction not found errors
    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ResponseError> handleTransactionNotFoundException(TransactionNotFoundException exception) {
        ResponseError error = new ResponseError();
        error.setMessage(exception.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // handle Insufficient Funds errors
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ResponseError> handleInsufficientFundsException(InsufficientFundsException exception) {
        ResponseError error = new ResponseError();
        error.setMessage(exception.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // handle generic runtime exceptions
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseError> handleRuntimeException(RuntimeException exception) {
        ResponseError error = new ResponseError();
        error.setMessage("An unexpected error occurred: " + exception.getMessage());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> handleGenericException(Exception exception) {
        ResponseError error = new ResponseError();
        error.setMessage("A server error occurred: " + exception.getMessage());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
