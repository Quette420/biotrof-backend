package ru.volgau.graduatework.biotrofbackend.service;

public interface MailSender {
    void send(String subject, String text, String... emailsTo);
}
