package com.tss.policy.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PolicyApiException extends RuntimeException {
    private String message;

    @Override
    public String getMessage() {
        return message;
    }
}
