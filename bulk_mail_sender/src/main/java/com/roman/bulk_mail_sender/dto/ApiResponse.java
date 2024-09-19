package com.roman.bulk_mail_sender.dto;

import lombok.Data;

@Data
public class ApiResponse {
    private boolean success;
    private String message;

    public ApiResponse() {}

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
