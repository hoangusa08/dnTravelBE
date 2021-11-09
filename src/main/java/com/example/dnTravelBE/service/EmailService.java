package com.example.dnTravelBE.service;

import com.example.dnTravelBE.dto.AcceptCodeDto;
import com.example.dnTravelBE.entity.Account;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;

public interface EmailService {
    //gửi mã code khi register
    void sendCodeVerifyMail(Account account) throws MessagingException;

    void resendEmail(Integer accountId) throws MessagingException;

    ResponseEntity<Object> acceptCode (AcceptCodeDto acceptCodeDto);
}
