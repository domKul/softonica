package com.softonicabackend.infrastructure.email;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

import static jakarta.mail.Transport.send;

@Component
public class LockEmailNotifier implements Observer {
    private final String smtpHost;
    private final String fromEmail;
    private final String smtpPort;


    public LockEmailNotifier(
            @Value("${spring.mail.host}") String smtpHost,
            @Value("${email.sender}") String fromEmail,
            @Value("${spring.mail.port}") String smtpPort) {
        this.smtpHost = smtpHost;
        this.fromEmail = fromEmail;
        this.smtpPort = smtpPort;
    }

    @Override
    public void update(String message, String toEmail) {
        sendEmail(message, toEmail);
    }

    private void sendEmail(String message, String toEmail) {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", smtpHost);
        properties.setProperty("mail.smtp.port", smtpPort);
        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(fromEmail));
            mimeMessage.addRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress(toEmail));
            mimeMessage.setSubject("Account Blocked");
            mimeMessage.setText(message);

            send(mimeMessage);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
