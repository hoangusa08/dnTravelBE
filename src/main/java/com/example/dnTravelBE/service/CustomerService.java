package com.example.dnTravelBE.service;

import com.example.dnTravelBE.dto.CustomerResponseLoginDto;
import com.example.dnTravelBE.dto.RegisterCustomerDto;
import com.example.dnTravelBE.entity.Account;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    void createCustomer(RegisterCustomerDto registerCustomerDto, Account newAccountCustomer);

    CustomerResponseLoginDto getCustomerWhenLogin(String email);

    CustomerResponseLoginDto getCustomerDetail (Integer id);

    void updateCustomerDetail( CustomerResponseLoginDto  customerResponseLoginDto);

    ResponseEntity  getAllCustomer( Integer page, String keyword);
}
