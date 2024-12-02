package com.ConnectMate.Services;

public interface EmailService {

    void sendEmail(String to, String subject, String body);

    void sendEmail(String[] to, String subject, String body);

    void sendEmailWithHTML(String[] to, String subject, String body);
}
