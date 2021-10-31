package com.example.dnTravelBE.service;

import com.example.dnTravelBE.dto.CustomerResponseLoginDto;
import com.example.dnTravelBE.dto.RegisterCustomerDto;
import com.example.dnTravelBE.entity.Account;

public interface CustomerService {
    void createCustomer(RegisterCustomerDto registerCustomerDto, Account newAccountCustomer);

    CustomerResponseLoginDto getCustomerWhenLogin(String email);
}
