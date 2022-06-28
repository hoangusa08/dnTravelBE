package com.example.dnTravelBE.service;

import com.example.dnTravelBE.constant.AccountRole;
import com.example.dnTravelBE.dto.RegisterCustomerDto;
import com.example.dnTravelBE.dto.RegisterProviderDto;
import com.example.dnTravelBE.entity.Account;
import com.example.dnTravelBE.request.ChangePassReq;
import org.springframework.http.ResponseEntity;

public interface AccountService {

    Account createAccountCustomer(RegisterCustomerDto registerCustomerDto);

    Account createAccountProvider(RegisterProviderDto registerProviderDto);

    AccountRole getRoleOfUser (String email);

    void updatePasswordCustomer (ChangePassReq changePassReq ,Integer id);

    void deleteCustomer( Integer customerId);
}
