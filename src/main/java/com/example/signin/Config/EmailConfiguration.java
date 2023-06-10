package com.example.signin.Config;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@Slf4j
public class EmailConfiguration {
    private static final String PROPERTIES_FILE = "application.properties";
    public void sendMessage(String to, String content) {
        Properties props = loadProperties();
        String from = props.getProperty("mail.from");
        String password = props.getProperty("mail.password");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from,password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Test message");
            message.setText(content);
            Transport.send(message);
            log.debug("Successfully sent to " + to);
        }catch (MessagingException e){
            throw new RuntimeException(e);
        }
    }

    private static Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream inputStream = EmailConfiguration.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            props.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file: " + PROPERTIES_FILE, e);
        }
        return props;
    }
}
