package com.tss.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MailResponse {
    private String message;
    private boolean status;
}
