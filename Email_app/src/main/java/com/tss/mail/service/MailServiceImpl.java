package com.tss.mail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tss.mail.dto.MailRequest;
import com.tss.mail.dto.MailResponse;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public MailResponse sendMail(MailRequest request) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(request.getTo());
            message.setSubject(request.getSubject());
            message.setText(request.getBody());
            mailSender.send(message);
            return new MailResponse("Mail sent successfully!", true);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send mail: " + e.getMessage());
        }
    }
}
