package com.poly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailSenderDTO {
    private String from;
    private String to;
    private String subject;
    private String body;
    public MailSenderDTO(String to, String subject, String body) {
        this.from = "nguyenthin34hd@gmail.com";
        this.to = to;
        this.subject = subject;
        this.body = body;
    }
}
