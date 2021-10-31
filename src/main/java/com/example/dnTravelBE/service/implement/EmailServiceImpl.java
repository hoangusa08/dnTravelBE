package com.example.dnTravelBE.service.implement;

import com.example.dnTravelBE.email.SendMailService;
import com.example.dnTravelBE.entity.Account;
import com.example.dnTravelBE.entity.ConfirmationCodeEmail;
import com.example.dnTravelBE.repository.ConfirmationCodeMailRepository;
import com.example.dnTravelBE.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final SendMailService sendMailService;
    private final ConfirmationCodeMailRepository confirmationCodeMailRepository;

    @Value("${application.mail.timeExpired}")
    private long timeExpired;


    @Override
    public void sendCodeVerifyMail(Account account) throws MessagingException {
        try {
            String randomDigit = String.format("%06d", new Random().nextInt(999999));
            ConfirmationCodeEmail confirmationCodeEmail = new ConfirmationCodeEmail();
            confirmationCodeEmail.setAccount(account);
            confirmationCodeEmail.setCreatedAt(LocalDateTime.now());
            confirmationCodeEmail.setExpireAt(LocalDateTime.now().plusMinutes(timeExpired));
            confirmationCodeEmail.setToken(randomDigit);

            String emailContent = String.format(
                    "Hi there," +
                            "\n\nThis is your code: %s." +
                            "\nThis code expire at %d minutes", randomDigit, timeExpired);
            sendMailService.sendMail(
                    account.getEmail(),
                    "Confirm your email!",
                    emailContent
            );
            System.out.println("abc");
            confirmationCodeMailRepository.save(confirmationCodeEmail);
        } catch ( MessagingException | MailAuthenticationException e) {
            throw new MessagingException("Failed to send email");
        }

    }
}
