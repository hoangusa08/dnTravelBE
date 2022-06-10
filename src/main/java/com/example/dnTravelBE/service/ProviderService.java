package com.example.dnTravelBE.service;

import com.example.dnTravelBE.constant.StatusEnum;
import com.example.dnTravelBE.dto.*;
import com.example.dnTravelBE.entity.Account;

public interface ProviderService {

    void createProvider(RegisterProviderDto registerProviderDto, Account account);

    ProviderResLoginDto getProviderWhenLogin(String email);

    void changeStatusProvider(Integer id, StatusEnum statusEnum);

    ResponseProviderDto getAllProviderByStatus(StatusEnum statusEnum, Integer page, String keyword);
}
