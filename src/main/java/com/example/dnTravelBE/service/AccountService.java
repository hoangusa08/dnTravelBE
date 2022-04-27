package com.example.dnTravelBE.service;

import com.example.dnTravelBE.constant.AccountRole;
import com.example.dnTravelBE.dto.RegisterCustomerDto;
import com.example.dnTravelBE.entity.Account;
import org.springframework.http.ResponseEntity;

public interface AccountService {

    Account createAccountCustomer(RegisterCustomerDto registerCustomerDto);

    AccountRole getRoleOfUser (String email);
}
