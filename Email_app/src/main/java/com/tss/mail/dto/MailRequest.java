package com.tss.mail.dto;

import lombok.Data;

@Data
public class MailRequest {
    private String to;
    private String subject;
    private String body;
}
