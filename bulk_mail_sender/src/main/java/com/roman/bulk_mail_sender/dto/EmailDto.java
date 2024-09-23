package com.roman.bulk_mail_sender.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class EmailDto {
    private String to;
    private String subject;
}
