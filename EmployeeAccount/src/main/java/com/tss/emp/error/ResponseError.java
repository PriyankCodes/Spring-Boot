package com.tss.emp.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseError {
    private int status;
    private long timestamp;
    private String message;
}
