package com.tss.mail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tss.mail.dto.MailRequest;
import com.tss.mail.dto.MailResponse;
import com.tss.mail.service.MailService;

@RestController
@RequestMapping("/api")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/send")
    public MailResponse sendMail(@RequestBody MailRequest request) {
        return mailService.sendMail(request);
    }
}
