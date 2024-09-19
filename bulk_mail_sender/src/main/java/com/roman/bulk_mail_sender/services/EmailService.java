package com.roman.bulk_mail_sender.services;


import com.roman.bulk_mail_sender.dto.EmailDto;
import jakarta.mail.MessagingException;

import java.util.List;

public interface EmailService {
    void sendBulkEmail(List<EmailDto> emailList) throws MessagingException;
}
