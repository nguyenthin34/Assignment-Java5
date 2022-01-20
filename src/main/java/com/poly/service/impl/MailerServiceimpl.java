package com.poly.service.impl;

import com.poly.dto.MailSenderDTO;
import com.poly.service.MailerService;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

@Service
public class MailerServiceimpl implements MailerService {
    @Autowired
    JavaMailSender sender;

    @Override
    public void send(MailSenderDTO mail) throws MessagingException
    {
        // Tạo message
        MimeMessage message = sender.createMimeMessage();
        // Sử dụng Helper để thiết lập các thông tin cần thiết cho message
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setFrom(mail.getFrom());
        helper.setTo(mail.getTo());
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getBody(), true);
        helper.setReplyTo(mail.getFrom());
        // Gửi message đến SMTP server
        sender.send(message);
    }

    @Override
    public void send(String to, String subject, String body) throws MessagingException {
        this.send(to, subject, body);
    }

    List<MailSenderDTO> list = new ArrayList<>();
    @Override
    public void queue(MailSenderDTO mail) {
        list.add(mail);
    }

    @Override
    public void queue(String to, String subject, String body) {
        queue(new MailSenderDTO(to, subject, body));
    }
    @Scheduled(fixedDelay = 5000)
    public void run() {
        while(!list.isEmpty()) {
            MailSenderDTO mail = list.remove(0);
            try {
                this.send(mail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
