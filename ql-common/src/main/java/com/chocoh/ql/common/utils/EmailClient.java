package com.chocoh.ql.common.utils;


import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * @author chocoh
 */
public class EmailClient {
    @Value("${email.from}")
    private String from;
    @Value("${email.host}")
    private String host;
    @Value("${email.password}")
    private String password;
    @Value("${email.type}")
    private String type;
    @Value("${email.username}")
    private String username;

    public void sendEmail(String receiver, String subject, String content) throws GeneralSecurityException, MessagingException {
        Properties properties = new Properties();
        properties.setProperty("mail.host", host);
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        session.setDebug(true);
        Transport ts = session.getTransport();
        ts.connect(host, from, password);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
        message.setSubject(subject);
        message.setContent(content, type);
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }
}
