package com.tss.hibernateDemo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentApiException extends RuntimeException {
    private String message;

    @Override
    public String getMessage() {
        return message;
    }
}

