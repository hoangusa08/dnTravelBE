package com.example.dnTravelBE.service.implement;

import com.example.dnTravelBE.dto.AcceptCodeDto;
import com.example.dnTravelBE.email.SendMailService;
import com.example.dnTravelBE.entity.Account;
import com.example.dnTravelBE.entity.ConfirmationCodeEmail;
import com.example.dnTravelBE.exception.FailException;
import com.example.dnTravelBE.exception.NotFoundException;
import com.example.dnTravelBE.repository.AccountRepository;
import com.example.dnTravelBE.repository.ConfirmationCodeMailRepository;
import com.example.dnTravelBE.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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
    private final AccountRepository accountRepository;

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
            System.out.println("abc1");
            sendMailService.sendMail(
                    account.getEmail(),
                    "Confirm your email!",
                    emailContent
            );

            confirmationCodeMailRepository.save(confirmationCodeEmail);
        } catch (MessagingException | MailAuthenticationException e) {
            throw new MessagingException("Failed to send email");
        }

    }

    @Override
    public void resendEmail(Integer accountId) throws MessagingException {
        ConfirmationCodeEmail confirmationCodeEmail = confirmationCodeMailRepository.findByAccount_Id(accountId).get();
        try {
            String randomDigit = String.format("%06d", new Random().nextInt(999999));
            confirmationCodeEmail.setCreatedAt(LocalDateTime.now());
            confirmationCodeEmail.setExpireAt(LocalDateTime.now().plusMinutes(timeExpired));
            confirmationCodeEmail.setToken(randomDigit);

            String emailContent = String.format(
                    "Hi there," +
                            "\n\nThis is your code: %s." +
                            "\nThis code expire at %d minutes", randomDigit, timeExpired);
            sendMailService.sendMail(
                    confirmationCodeEmail.getAccount().getEmail(),
                    "Confirm your email!",
                    emailContent
            );
            confirmationCodeMailRepository.save(confirmationCodeEmail);
        } catch (MessagingException | MailAuthenticationException e) {
            throw new MessagingException("Failed to send email");
        }
    }

    @Override
    public ResponseEntity<Object> acceptCode(AcceptCodeDto acceptCodeDto) {
        ConfirmationCodeEmail confirmationCodeEmail = confirmationCodeMailRepository.findByAccount_Id(acceptCodeDto.getAccountId())
                .orElseThrow(() -> new NotFoundException("Account does not existed.", 2049));
        if (confirmationCodeEmail.getToken() == acceptCodeDto.getCode()) {
            if(LocalDateTime.now().isBefore(confirmationCodeEmail.getExpireAt())){
                confirmationCodeEmail.setConfirmedAt(LocalDateTime.now());
                confirmationCodeMailRepository.save(confirmationCodeEmail);
                Account account = confirmationCodeEmail.getAccount();
                account.setConfirmEmail(true);
                accountRepository.save(account);
            }else {
                throw new FailException("Code is expired, please click resend code", 2051);
            }
        } else {
            throw new FailException("Code invalid", 2050);
        }
        return null;
    }
}
