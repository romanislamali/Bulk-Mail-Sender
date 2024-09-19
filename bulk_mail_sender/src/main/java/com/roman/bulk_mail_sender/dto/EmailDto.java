package com.roman.bulk_mail_sender.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class EmailDto {
    private String to;
    private String subject;
    private String text;
    private String footer;
    private String cc;
    private String bcc;
    private List<String> emails;
    private MultipartFile[] attachments;
}
