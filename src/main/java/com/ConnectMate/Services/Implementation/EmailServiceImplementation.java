package com.ConnectMate.Services.Implementation;

import com.ConnectMate.Helpers.AppConstants;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ConnectMate.Services.EmailService;

@Service
public class EmailServiceImplementation implements EmailService {

    @Autowired
    private JavaMailSender eMailSender;

    @Value("${spring.mail.properties.domain_name}")
    private String domainName;

    @Override
    public void sendEmail(String to, String subject, String htmlBody) {
        String fullMessage = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Connect Mate - Admin Email</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            color: #333;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            background-color: #f7f7f7;\n" +
                "        }\n" +
                "        .email-container {\n" +
                "            width: 100%;\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            background-color: #ffffff;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 8px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        .header {\n" +
                "            background-color: #007BFF;\n" +
                "            color: #fff;\n" +
                "            text-align: center;\n" +
                "            padding: 10px 0;\n" +
                "            border-radius: 8px 8px 0 0;\n" +
                "        }\n" +
                "        .header h1 {\n" +
                "            margin: 0;\n" +
                "        }\n" +
                "        .content {\n" +
                "            padding: 20px;\n" +
                "            line-height: 1.6;\n" +
                "        }\n" +
                "        .button {\n" +
                "            display: inline-block;\n" +
                "            background-color: #007BFF;\n" +
                "            color: #ffffff;\n" +
                "            padding: 10px 20px;\n" +
                "            font-size: 16px;\n" +
                "            text-decoration: none;\n" +
                "            border-radius: 5px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            text-align: center;\n" +
                "            padding: 10px;\n" +
                "            font-size: 14px;\n" +
                "            color: #888;\n" +
                "        }\n" +
                "        .footer a {\n" +
                "            color: #007BFF;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "    <div class=\"email-container\">\n" +
                "        <div class=\"header\">\n" +
                "            <h1>Connect Mate</h1>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"content\">\n" +
                "            <!-- Single User Content -->\n" +
                "            <p>Hello [User Name],</p>\n" +
                "            <p>You are receiving this email from the Connect Mate admin regarding an important update. Here's the message:</p>\n" +
                "            <p><strong>"+htmlBody+"</strong></p>\n" +
                "            <p>If you have any questions or need assistance, feel free to reach out to us.</p>\n" +
                "            <!-- <a href=\"[Reply Link]\" class=\"button\">Reply to Admin</a> -->\n" +
                "\n" +
                "            <!-- Multiple Users Content -->\n" +
                "            <p>If you're receiving this email along with others, it’s because Connect Mate's admin has sent out an important update to multiple users.</p>\n" +
                "            <p>If you did not expect to receive this email or have any questions, feel free to reply to this message for more information.</p>\n" +
                "\n" +
                "            <p>Thank you for being a part of Connect Mate!</p>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"footer\">\n" +
                "            <p>Connect Mate &copy; 2024</p>\n" +
                "            <p><a href=\"[Privacy Policy Link]\">Privacy Policy</a> | <a href=\"[Unsubscribe Link]\">Unsubscribe</a></p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n" ;
        try {
            MimeMessage mimeMailMessage = eMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMailMessage, true, "UTF-8");
            messageHelper.setFrom(AppConstants.EMAIL_FROM);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(fullMessage, true);
            eMailSender.send(mimeMailMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending email: " + e.getMessage(), e);
        }
    }

    @Override
    public void sendEmailWithHTML(String[] to, String subject, String htmlBody) {
        String fullmessage = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Connect Mate - Admin Email</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            color: #333;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            background-color: #f7f7f7;\n" +
                "        }\n" +
                "        .email-container {\n" +
                "            width: 100%;\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            background-color: #ffffff;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 8px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        .header {\n" +
                "            background-color: #007BFF;\n" +
                "            color: #fff;\n" +
                "            text-align: center;\n" +
                "            padding: 10px 0;\n" +
                "            border-radius: 8px 8px 0 0;\n" +
                "        }\n" +
                "        .header h1 {\n" +
                "            margin: 0;\n" +
                "        }\n" +
                "        .content {\n" +
                "            padding: 20px;\n" +
                "            line-height: 1.6;\n" +
                "        }\n" +
                "        .button {\n" +
                "            display: inline-block;\n" +
                "            background-color: #007BFF;\n" +
                "            color: #ffffff;\n" +
                "            padding: 10px 20px;\n" +
                "            font-size: 16px;\n" +
                "            text-decoration: none;\n" +
                "            border-radius: 5px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            text-align: center;\n" +
                "            padding: 10px;\n" +
                "            font-size: 14px;\n" +
                "            color: #888;\n" +
                "        }\n" +
                "        .footer a {\n" +
                "            color: #007BFF;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "    <div class=\"email-container\">\n" +
                "        <div class=\"header\">\n" +
                "            <h1>Connect Mate</h1>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"content\">\n" +
                "            <!-- Single User Content -->\n" +
                "            <p>Hello [User Name],</p>\n" +
                "            <p>You are receiving this email from the Connect Mate admin regarding an important update. Here's the message:</p>\n" +
                "            <p><strong>"+htmlBody+"</strong></p>\n" +
                "            <p>If you have any questions or need assistance, feel free to reach out to us.</p>\n" +
                "            <!-- <a href=\"[Reply Link]\" class=\"button\">Reply to Admin</a> -->\n" +
                "\n" +
                "            <!-- Multiple Users Content -->\n" +
                "            <p>If you're receiving this email along with others, it’s because Connect Mate's admin has sent out an important update to multiple users.</p>\n" +
                "            <p>If you did not expect to receive this email or have any questions, feel free to reply to this message for more information.</p>\n" +
                "\n" +
                "            <p>Thank you for being a part of Connect Mate!</p>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"footer\">\n" +
                "            <p>Connect Mate &copy; 2024</p>\n" +
                "            <p><a href=\"[Privacy Policy Link]\">Privacy Policy</a> | <a href=\"[Unsubscribe Link]\">Unsubscribe</a></p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";
        try {
            MimeMessage mimeMailMessage = eMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMailMessage, true, "UTF-8");
            messageHelper.setFrom(AppConstants.EMAIL_FROM);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(fullmessage, true);
            eMailSender.send(mimeMailMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending email: " + e.getMessage(), e);
        }
    }

}
