package com.example.dnTravelBE.service;

import com.example.dnTravelBE.dto.RegisterCustomerDto;
import org.springframework.http.ResponseEntity;

public interface AccountService {

    ResponseEntity<Object> createAccountCustomer(RegisterCustomerDto registerCustomerDto);
}
