package com.example.dnTravelBE.service;

import com.example.dnTravelBE.entity.Account;

import javax.mail.MessagingException;

public interface EmailService {


    void sendCodeVerifyMail(Account account) throws MessagingException;
}
