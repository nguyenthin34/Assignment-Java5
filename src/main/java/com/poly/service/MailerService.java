package com.poly.service;

import com.poly.dto.MailSenderDTO;

import javax.mail.MessagingException;

public interface MailerService {
    /**
     * Gửi email
     *
     * @param mail thông tin email
     * @throws MessagingException lỗi gửi email
     */
    void send(MailSenderDTO mail) throws MessagingException;

    /**
     * Gửi email đơn giản
     *
     * @param to      email người nhận
     * @param subject tiêu đề email
     * @param body    nội dung email
     * @throws MessagingException lỗi gửi email
     */
    void send(String to, String subject, String body) throws MessagingException;

    /**
     * Xếp mail vào hàng đợi
     *
     * @param mail thông tin email
     */
    void queue(MailSenderDTO mail);

    /**
     * Tạo MailInfo và xếp vào hàng đợi
     *
     * @param to email người nhận
     * @param subject tiêu đề email
     * @param body    nội dung email
     */
    void queue(String to, String subject, String body);
}
