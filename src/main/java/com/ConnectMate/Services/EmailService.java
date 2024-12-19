package com.ConnectMate.Services;

public interface EmailService {

    void sendEmail(String to, String subject, String body);

    void sendEmailWithHTML(String[] to, String subject, String body);

    void sendQuery(String to,String name,String queryId,String subject, String body, String image);
}
