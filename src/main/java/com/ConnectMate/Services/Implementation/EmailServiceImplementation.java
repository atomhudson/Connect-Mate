package com.ConnectMate.Services.Implementation;

import com.ConnectMate.Helpers.AppConstants;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;



import com.ConnectMate.Services.EmailService;

import static org.hibernate.sql.ast.SqlTreeCreationLogger.LOGGER;

@Service
public class EmailServiceImplementation implements EmailService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender eMailSender;

    @Value("${spring.mail.properties.domain_name}")
    private String domainName;

    @Override
    public void sendEmail(String to, String subject, String htmlBody) {
        // Build the email content dynamically
        String fullMessage = """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Connect Mate - Admin Email</title>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    color: #333;
                    margin: 0;
                    padding: 0;
                    background-color: #f7f7f7;
                }
                .email-container {
                    width: 100%%;
                    max-width: 600px;
                    margin: 0 auto;
                    background-color: #ffffff;
                    padding: 20px;
                    border-radius: 8px;
                    box-shadow: 10px 10px 10px rgba(0, 0, 0, 0.1);
                    border: 1px solid #ddd;
                }
                .header {
                    background-color: #007BFF;
                    color: #fff;
                    text-align: center;
                    padding: 10px 0;
                    border-radius: 8px 8px 0 0;
                }
                .header h1 {
                    margin: 0;
                }
                .content {
                    padding: 20px;
                    line-height: 1.6;
                }
                .footer {
                    text-align: center;
                    padding: 10px;
                    font-size: 14px;
                    color: #888;
                }
                .footer a {
                    color: #007BFF;
                    text-decoration: none;
                }
            </style>
        </head>
        <body>
            <div class="email-container">
                <div class="header">
                    <h1>Connect Mate</h1>
                </div>
                <div class="content">
                    <p>Hello %s,</p>
                    <p>You are receiving this email from the Connect Mate admin regarding an important update. Here's the message:</p>
                    <p><strong>%s</strong></p>
                    <p>If you have any questions or need assistance, feel free to reach out to us.</p>
                    <p>Thank you for being a part of Connect Mate!</p>
                </div>
                <div class="footer">
                    <p>Connect Mate &copy; 2024</p>
                    <p><a href="[Privacy Policy Link]">Privacy Policy</a> | <a href="[Unsubscribe Link]">Unsubscribe</a></p>
                </div>
            </div>
        </body>
        </html>
        """.formatted(to, htmlBody);

        try {
            MimeMessage mimeMailMessage = eMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMailMessage, true, "UTF-8");
            messageHelper.setFrom(AppConstants.EMAIL_FROM);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(fullMessage, true);
            eMailSender.send(mimeMailMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending email to " + to + ": " + e.getMessage(), e);
        }
    }
    @Override
    public void sendEmailWithHTML(String[] to, String subject, String htmlBody) {
        for (String recipient : to) {
            String fullMessage = String.format("""
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Connect Mate - Admin Email</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        color: #333;
                        margin: 0;
                        padding: 0;
                        background-color: #f7f7f7;
                    }
                    .email-container {
                        width: 100%%;
                        max-width: 600px;
                        margin: 0 auto;
                        background-color: #ffffff;
                        padding: 20px;
                        border-radius: 8px;
                        box-shadow: 10px 10px 10px rgba(0, 0, 0, 0.1);
                        border: 1px solid #ddd;
                    }
                    .header {
                        background-color: #007BFF;
                        color: #fff;
                        text-align: center;
                        padding: 10px 0;
                        border-radius: 8px 8px 0 0;
                    }
                    .header h1 {
                        margin: 0;
                    }
                    .content {
                        padding: 20px;
                        line-height: 1.6;
                    }
                    .footer {
                        text-align: center;
                        padding: 10px;
                        font-size: 14px;
                        color: #888;
                    }
                    .footer a {
                        color: #007BFF;
                        text-decoration: none;
                    }
                </style>
            </head>
            <body>
                <div class="email-container">
                    <div class="header">
                        <h1>Connect Mate</h1>
                    </div>
                    <div class="content">
                        <p>Hello %s,</p>
                        <p>You are receiving this email from the Connect Mate admin regarding an important update. Here's the message:</p>
                        <p><strong>%s</strong></p>
                        <p>If you have any questions or need assistance, feel free to reach out to us.</p>
                        <p>Thank you for being a part of Connect Mate!</p>
                    </div>
                    <div class="footer">
                        <p>Connect Mate &copy; 2024</p>
                        <p><a href="[Privacy Policy Link]">Privacy Policy</a> | <a href="[Unsubscribe Link]">Unsubscribe</a></p>
                    </div>
                </div>
            </body>
            </html>
            """, recipient, htmlBody);

            try {
                MimeMessage mimeMessage = eMailSender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                messageHelper.setFrom(AppConstants.EMAIL_FROM);
                messageHelper.setTo(recipient);
                messageHelper.setSubject(subject);
                messageHelper.setText(fullMessage, true);
                eMailSender.send(mimeMessage);
            } catch (MessagingException e) {
                throw new RuntimeException("Error sending email to " + recipient + ": " + e.getMessage(), e);
            }
        }
    }
    @Override
    public void sendQuery(String to, String name, String queryId, String subject, String body, String image) {
        String emailMessage = "<!DOCTYPE html>\n" +
                "            <html lang=\"en\">\n" +
                "            <head>\n" +
                "                <meta charset=\"UTF-8\">\n" +
                "                <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "                <title>Connect Mate - Admin Email</title>\n" +
                "                <style>\n" +
                "                    body {\n" +
                "                        font-family: Arial, sans-serif;\n" +
                "                        color: #333;\n" +
                "                        margin: 0;\n" +
                "                        padding: 0;\n" +
                "                        background-color: #f7f7f7;\n" +
                "                    }\n" +
                "                    .email-container {\n" +
                "                        width: 100%;\n" +
                "                        max-width: 600px;\n" +
                "                        margin: 0 auto;\n" +
                "                        background-color: #ffffff;\n" +
                "                        padding: 20px;\n" +
                "                        border-radius: 8px;\n" +
                "                        box-shadow: 10px 10px 10px rgba(0, 0, 0, 0.1);\n" +
                "                        border: 1px solid #ddd;\n" +
                "                    }\n" +
                "                    .header {\n" +
                "                        background-color: #007BFF;\n" +
                "                        color: #fff;\n" +
                "                        text-align: center;\n" +
                "                        padding: 10px 0;\n" +
                "                        border-radius: 8px 8px 0 0;\n" +
                "                    }\n" +
                "                    .header h1 {\n" +
                "                        margin: 0;\n" +
                "                    }\n" +
                "                    .content {\n" +
                "                        padding: 20px;\n" +
                "                        line-height: 1.6;\n" +
                "                    }\n" +
                "                    .content img {\n" +
                "                        max-width: 100%;\n" +
                "                        border-radius: 8px;\n" +
                "                        margin-top: 20px;\n" +
                "                    }\n" +
                "                    .footer {\n" +
                "                        text-align: center;\n" +
                "                        padding: 10px;\n" +
                "                        font-size: 14px;\n" +
                "                        color: #888;\n" +
                "                    }\n" +
                "                    .footer a {\n" +
                "                        color: #007BFF;\n" +
                "                        text-decoration: none;\n" +
                "                    }\n" +
                "                </style>\n" +
                "            </head>\n" +
                "            <body>\n" +
                "                <div class=\"email-container\">\n" +
                "                    <div class=\"header\">\n" +
                "                        <h1>Connect Mate</h1>\n" +
                "                    </div>\n" +
                "                    <div class=\"content\">\n" +
                "                        <p>Hello "+name+",</p>\n" +
                "                        <p>You are receiving this email from the Connect Mate admin regarding an important update. Here's the message:</p>\n" +
                "                        <p><strong>"+body+"</strong></p>\n" +
                "                        <p>We also wanted to let you know that your query has been successfully created! Your unique query ID is: <strong>"+queryId+"</strong></p>\n" +
                "                        <p>Below is a related image for your reference:</p>\n" +
                "                        <img src=\""+image+"\" alt=\"Related Image\">\n" +
                "                        <p>If you have any questions or need assistance, feel free to reach out to us.</p>\n" +
                "                        <p>Thank you for being a part of Connect Mate!</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"footer\">\n" +
                "                        <p>Connect Mate &copy; 2024</p>\n" +
                "                        <p><a href=\"[Privacy Policy Link]\">Privacy Policy</a> | <a href=\"[Unsubscribe Link]\">Unsubscribe</a></p>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </body>\n" +
                "            </html>";
        try {
            MimeMessage mimeMessage = eMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setFrom(AppConstants.EMAIL_FROM);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(emailMessage, true);
            eMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.info("Error sending email to " + to + ": " + e.getMessage());
            throw new RuntimeException("Error sending email to " + to + ": " + e.getMessage(), e);
        }
    }
    @Override
    public void queryResolved(String to, String name, String queryId, String subject, String body, String image) {
        String emailTemplate =
                "<!DOCTYPE html>\n" +
                        "            <html lang=\"en\">\n" +
                        "            <head>\n" +
                        "                <meta charset=\"UTF-8\">\n" +
                        "                <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "                <title>Connect Mate - Admin Email</title>\n" +
                        "                <style>\n" +
                        "                    body {\n" +
                        "                        font-family: Arial, sans-serif;\n" +
                        "                        color: #333;\n" +
                        "                        margin: 0;\n" +
                        "                        padding: 0;\n" +
                        "                        background-color: #f7f7f7;\n" +
                        "                    }\n" +
                        "                    .email-container {\n" +
                        "                        width: 100%;\n" +
                        "                        max-width: 600px;\n" +
                        "                        margin: 0 auto;\n" +
                        "                        background-color: #ffffff;\n" +
                        "                        padding: 20px;\n" +
                        "                        border-radius: 8px;\n" +
                        "                        box-shadow: 10px 10px 10px rgba(0, 0, 0, 0.1);\n" +
                        "                        border: 1px solid #ddd;\n" +
                        "                    }\n" +
                        "                    .header {\n" +
                        "                        background-color: #007BFF;\n" +
                        "                        color: #fff;\n" +
                        "                        text-align: center;\n" +
                        "                        padding: 10px 0;\n" +
                        "                        border-radius: 8px 8px 0 0;\n" +
                        "                    }\n" +
                        "                    .header h1 {\n" +
                        "                        margin: 0;\n" +
                        "                    }\n" +
                        "                    .content {\n" +
                        "                        padding: 20px;\n" +
                        "                        line-height: 1.6;\n" +
                        "                    }\n" +
                        "                    .content img {\n" +
                        "                        max-width: 100%;\n" +
                        "                        border-radius: 8px;\n" +
                        "                        margin-top: 20px;\n" +
                        "                    }\n" +
                        "                    .footer {\n" +
                        "                        text-align: center;\n" +
                        "                        padding: 10px;\n" +
                        "                        font-size: 14px;\n" +
                        "                        color: #888;\n" +
                        "                    }\n" +
                        "                    .footer a {\n" +
                        "                        color: #007BFF;\n" +
                        "                        text-decoration: none;\n" +
                        "                    }\n" +
                        "                </style>\n" +
                        "            </head>\n" +
                        "            <body>\n" +
                        "                <div class=\"email-container\">\n" +
                        "                    <div class=\"header\">\n" +
                        "                        <h1>Connect Mate</h1>\n" +
                        "                    </div>\n" +
                        "                    <div class=\"content\">\n" +
                        "                        <p>Hello "+name+",</p>\n" +
                        "                        <p>We are pleased to inform you that your query has been <strong>successfully resolved!</strong> Your query details are as follows:</p>\n" +
                        "                        <p><strong>"+body+"</strong></p>\n" +
                        "                        <p>Your unique query ID is: <strong>"+queryId+"</strong></p>\n" +
                        "                        <p>Below is a related image for your reference:</p>\n" +
                        "                        <img src=\""+image+"\" alt=\"Related Image\">\n" +
                        "                        <p>If you have any further questions or need assistance, feel free to reach out to us.</p>\n" +
                        "                        <p>Thank you for being a part of Connect Mate!</p>\n" +
                        "                    </div>\n" +
                        "                    <div class=\"footer\">\n" +
                        "                        <p>Connect Mate &copy; 2024</p>\n" +
                        "                        <p><a href=\"[Privacy Policy Link]\">Privacy Policy</a> | <a href=\"[Unsubscribe Link]\">Unsubscribe</a></p>\n" +
                        "                    </div>\n" +
                        "                </div>\n" +
                        "            </body>\n" +
                        "            </html>";

        try {
            MimeMessage mimeMessage = eMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setFrom(AppConstants.EMAIL_FROM);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(emailTemplate, true);
            eMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.info("Error sending email to " + to + ": " + e.getMessage());
            throw new RuntimeException("Error sending email to " + to + ": " + e.getMessage(), e);
        }
    }
    @Override
    public void queryDeleted(String to, String name, String queryId, String subject, String body, String image) {
        String emailTemplate = "<!DOCTYPE html>\n" +
                        "            <html lang=\"en\">\n" +
                        "            <head>\n" +
                        "                <meta charset=\"UTF-8\">\n" +
                        "                <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "                <title>Connect Mate - Admin Email</title>\n" +
                        "                <style>\n" +
                        "                    body {\n" +
                        "                        font-family: Arial, sans-serif;\n" +
                        "                        color: #333;\n" +
                        "                        margin: 0;\n" +
                        "                        padding: 0;\n" +
                        "                        background-color: #f7f7f7;\n" +
                        "                    }\n" +
                        "                    .email-container {\n" +
                        "                        width: 100%;\n" +
                        "                        max-width: 600px;\n" +
                        "                        margin: 0 auto;\n" +
                        "                        background-color: #ffffff;\n" +
                        "                        padding: 20px;\n" +
                        "                        border-radius: 8px;\n" +
                        "                        box-shadow: 10px 10px 10px rgba(0, 0, 0, 0.1);\n" +
                        "                        border: 1px solid #ddd;\n" +
                        "                    }\n" +
                        "                    .header {\n" +
                        "                        background-color: #007BFF;\n" +
                        "                        color: #fff;\n" +
                        "                        text-align: center;\n" +
                        "                        padding: 10px 0;\n" +
                        "                        border-radius: 8px 8px 0 0;\n" +
                        "                    }\n" +
                        "                    .header h1 {\n" +
                        "                        margin: 0;\n" +
                        "                    }\n" +
                        "                    .content {\n" +
                        "                        padding: 20px;\n" +
                        "                        line-height: 1.6;\n" +
                        "                    }\n" +
                        "                    .content img {\n" +
                        "                        max-width: 100%;\n" +
                        "                        border-radius: 8px;\n" +
                        "                        margin-top: 20px;\n" +
                        "                    }\n" +
                        "                    .footer {\n" +
                        "                        text-align: center;\n" +
                        "                        padding: 10px;\n" +
                        "                        font-size: 14px;\n" +
                        "                        color: #888;\n" +
                        "                    }\n" +
                        "                    .footer a {\n" +
                        "                        color: #007BFF;\n" +
                        "                        text-decoration: none;\n" +
                        "                    }\n" +
                        "                </style>\n" +
                        "            </head>\n" +
                        "            <body>\n" +
                        "                <div class=\"email-container\">\n" +
                        "                    <div class=\"header\">\n" +
                        "                        <h1>Connect Mate</h1>\n" +
                        "                    </div>\n" +
                        "                    <div class=\"content\">\n" +
                        "                        <p>Hello "+name+",</p>\n" +
                        "                        <p><strong>We regret to inform you that your query is no longer valid and has been permanently deleted from our system.</strong> The query ID <strong>"+queryId+"</strong> no longer exists in our records.</p>\n" +
                        "                        <p>We apologize for any inconvenience this may have caused.</p>\n" +
                        "                        <p>If you have any further questions or need assistance, feel free to reach out to us.</p>\n" +
                        "                        <p>Thank you for being a part of Connect Mate!</p>\n" +
                        "                    </div>\n" +
                        "                    <div class=\"footer\">\n" +
                        "                        <p>Connect Mate &copy; 2024</p>\n" +
                        "                        <p><a href=\"[Privacy Policy Link]\">Privacy Policy</a> | <a href=\"[Unsubscribe Link]\">Unsubscribe</a></p>\n" +
                        "                    </div>\n" +
                        "                </div>\n" +
                        "            </body>\n" +
                        "            </html>";
        try {
            MimeMessage mimeMessage = eMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setFrom(AppConstants.EMAIL_FROM);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(emailTemplate, true);
            eMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.info("Error sending email to " + to + ": " + e.getMessage());
            throw new RuntimeException("Error sending email to " + to + ": " + e.getMessage(), e);
        }
    }
}
