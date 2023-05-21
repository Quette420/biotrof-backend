package ru.volgau.graduatework.biotrofbackend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.volgau.graduatework.biotrofbackend.service.MailSender;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailSenderImpl implements MailSender {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    @Override
    public void send(String subject, String text, String... emailsTo) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(emailsTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        try {
            log.info(String.valueOf(mailMessage));
            mailSender.send(mailMessage);
        } catch (MailSendException e) {
            log.info(e.getMessage());
        }
    }
}
