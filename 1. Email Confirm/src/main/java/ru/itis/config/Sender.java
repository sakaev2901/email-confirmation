package ru.itis.config;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Sender {
    private String username;
    private String password;
    private Properties props;

    public Sender(String username, String password, Properties properties) {
        this.username = username;
        this.password = password;
        this.props = properties;
    }

    public void send(String subject, String text, String fromEmail, String toEmail){
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(text);

            message.setContent(text, "text/html");

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
