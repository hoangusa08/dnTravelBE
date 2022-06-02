package com.example.dnTravelBE.service;

import com.example.dnTravelBE.constant.StatusEnum;
import com.example.dnTravelBE.dto.CustomerResponseLoginDto;
import com.example.dnTravelBE.dto.ProviderResLoginDto;
import com.example.dnTravelBE.dto.RegisterCustomerDto;
import com.example.dnTravelBE.dto.RegisterProviderDto;
import com.example.dnTravelBE.entity.Account;

public interface ProviderService {

    void createProvider(RegisterProviderDto registerProviderDto, Account account);

    ProviderResLoginDto getProviderWhenLogin(String email);

    void changeStatusProvider(Integer id, StatusEnum statusEnum);
}
