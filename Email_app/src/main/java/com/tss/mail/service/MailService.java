package com.tss.mail.service;

import com.tss.mail.dto.MailRequest;
import com.tss.mail.dto.MailResponse;

public interface MailService {
    MailResponse sendMail(MailRequest request);
}
