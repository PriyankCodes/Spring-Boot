package com.tss.bank.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BankApiException extends RuntimeException {
    private String message;

    @Override
    public String getMessage() {
        return message;
    }
}
