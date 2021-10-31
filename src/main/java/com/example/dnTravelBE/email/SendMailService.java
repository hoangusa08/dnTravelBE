package com.example.dnTravelBE.email;

import lombok.AllArgsConstructor;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class SendMailService {
    private final JavaMailSender javaMailSender;

    public void sendMail (String to , String body , String topic) throws MessagingException, MailAuthenticationException {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            simpleMailMessage.setFrom("java-master@gmail.com");
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(topic);
            simpleMailMessage.setText(body);
            javaMailSender.send(simpleMailMessage);
        } catch (MailAuthenticationException e) {
            throw new MailAuthenticationException("Failed to send email!");
        }
        ;

    }
}
