package com.tss.emp.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeApiException extends RuntimeException {
    private String message;

    @Override
    public String getMessage() {
        return message;
    }
}
