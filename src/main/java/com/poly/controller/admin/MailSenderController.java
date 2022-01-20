package com.poly.controller.admin;

import com.poly.dto.MailSenderDTO;
import com.poly.service.MailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;

@Controller
@RequestMapping("admin/mailer")
public class MailSenderController {
    @Autowired
    MailerService mailerService;
    @GetMapping("send")
    public String send(MailSenderDTO dto) throws MessagingException {
        mailerService.send(dto);
        return"";
    }
}
