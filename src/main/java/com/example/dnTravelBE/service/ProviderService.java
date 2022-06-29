package com.example.dnTravelBE.service;

import com.example.dnTravelBE.constant.StatusEnum;
import com.example.dnTravelBE.dto.*;
import com.example.dnTravelBE.entity.Account;
import com.example.dnTravelBE.request.ProviderReq;
import org.springframework.http.ResponseEntity;

public interface ProviderService {

    void createProvider(RegisterProviderDto registerProviderDto, Account account);

    ProviderResLoginDto getProviderWhenLogin(String email);

    void changeStatusProvider(Integer id, StatusEnum statusEnum);

    ResponseProviderDto getAllProviderByStatus(StatusEnum statusEnum, Integer page, String keyword);

    ResponseEntity getTotalDashboard( Integer providerId);
    ResponseEntity getChatPaymentDashboard(Integer providerId, int year);

    ProviderResLoginDto getDetail( Integer id);

    void editProvider(ProviderReq providerReq);
}
