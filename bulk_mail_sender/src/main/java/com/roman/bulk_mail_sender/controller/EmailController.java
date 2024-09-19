package com.roman.bulk_mail_sender.controller;
import com.roman.bulk_mail_sender.dto.EmailDto;
import com.roman.bulk_mail_sender.services.EmailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("email/")
@AllArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("sendBulk")
    public String sendBulkEmail(@RequestBody List<EmailDto> emailList) throws MessagingException {
        return emailService.sendBulkEmail(emailList);
    }
}
