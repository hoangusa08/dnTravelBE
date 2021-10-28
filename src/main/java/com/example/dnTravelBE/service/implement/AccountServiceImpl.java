package com.example.dnTravelBE.service.implement;

import com.example.dnTravelBE.constant.AccountRole;
import com.example.dnTravelBE.dto.RegisterCustomerDto;
import com.example.dnTravelBE.dto.ResponseDto;
import com.example.dnTravelBE.entity.Account;
import com.example.dnTravelBE.entity.Role;
import com.example.dnTravelBE.exception.FailException;
import com.example.dnTravelBE.exception.NotFoundException;
import com.example.dnTravelBE.repository.AccountRepository;
import com.example.dnTravelBE.repository.RoleRepository;
import com.example.dnTravelBE.service.AccountService;
import com.example.dnTravelBE.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final RoleRepository roleRepository;
    @Override
    public ResponseEntity<Object> createAccountCustomer(RegisterCustomerDto registerCustomerDto) {
        Optional<Account> account = accountRepository.findByEmail(registerCustomerDto.getEmail());
        Optional<Role> role = roleRepository.findByName(AccountRole.ROLE_CUSTOMER);
        if (account.isEmpty()){
            Account accountCustomer = new Account();
            accountCustomer.setUsername(registerCustomerDto.getUsername());
            accountCustomer.setEmail(registerCustomerDto.getEmail());
            accountCustomer.setPassword(registerCustomerDto.getPassword());
            accountCustomer.setRole(role.get());
            accountCustomer.setCreateAt(LocalDate.now());
            try {
                Account newAccountCustomer = accountRepository.save(accountCustomer);
                customerService.createCustomer(registerCustomerDto , newAccountCustomer);
                return ResponseEntity.ok(ResponseDto.responseWithoutData());
            } catch (Exception e){
                throw new FailException("Can't create an account", 1001);
            }

        }else {
            throw new FailException("Account is allready existed." , 1000);
        }

    }
}
