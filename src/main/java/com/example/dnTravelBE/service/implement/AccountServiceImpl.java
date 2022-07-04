package com.example.dnTravelBE.service.implement;

import com.example.dnTravelBE.constant.AccountRole;
import com.example.dnTravelBE.dto.RegisterCustomerDto;
import com.example.dnTravelBE.dto.RegisterProviderDto;
import com.example.dnTravelBE.entity.Account;
import com.example.dnTravelBE.entity.Customer;
import com.example.dnTravelBE.entity.Role;
import com.example.dnTravelBE.exception.FailException;
import com.example.dnTravelBE.exception.NotFoundException;
import com.example.dnTravelBE.repository.AccountRepository;
import com.example.dnTravelBE.repository.CustomerRepository;
import com.example.dnTravelBE.repository.RoleRepository;
import com.example.dnTravelBE.request.ChangePassReq;
import com.example.dnTravelBE.request.EditAvatarCus;
import com.example.dnTravelBE.service.AccountService;
import com.example.dnTravelBE.service.CustomerService;
import com.example.dnTravelBE.service.ProviderService;
import lombok.AllArgsConstructor;
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
    private final ProviderService providerService;

    private final CustomerRepository customerRepository;

    @Override
    public Account createAccountCustomer(RegisterCustomerDto registerCustomerDto) {
        Optional<Account> account = accountRepository.findByEmail(registerCustomerDto.getEmail());
        Role role = roleRepository.findByName(AccountRole.ROLE_CUSTOMER)
                .orElseThrow(() -> new NotFoundException("Role input invalid", 1004));
        if (account.isEmpty()) {
            Account accountCustomer = new Account();
            accountCustomer.setUsername(registerCustomerDto.getUsername());
            accountCustomer.setEmail(registerCustomerDto.getEmail());
            accountCustomer.setPassword(registerCustomerDto.getPassword());
            accountCustomer.setRole(role);
            accountCustomer.setCreateAt(LocalDate.now());
            try {
                Account newAccountCustomer = accountRepository.save(accountCustomer);
                customerService.createCustomer(registerCustomerDto, newAccountCustomer);
                return newAccountCustomer;
            } catch (Exception e) {
                throw new FailException("Can't create an account", 1001);
            }

        } else {
            throw new FailException("Account is allready existed.", 1000);
        }
    }

    @Override
    public Account createAccountProvider(RegisterProviderDto registerProviderDto) {
        Optional<Account> account = accountRepository.findByEmail(registerProviderDto.getEmail());
        Role role = roleRepository.findByName(AccountRole.ROLE_PROVIDER)
                .orElseThrow(() -> new NotFoundException("Role input invalid", 1004));
        if (account.isEmpty()) {
            Account accountProvider = new Account();
            accountProvider.setUsername(registerProviderDto.getUsername());
            accountProvider.setEmail(registerProviderDto.getEmail());
            accountProvider.setPassword(registerProviderDto.getPassword());
            accountProvider.setRole(role);
            accountProvider.setCreateAt(LocalDate.now());
            try {
                Account newAccountProvider = accountRepository.save(accountProvider);
                providerService.createProvider(registerProviderDto, newAccountProvider);
                return newAccountProvider;
            } catch (Exception e) {
                throw new FailException("Can't create an account", 1008);
            }

        } else {
            throw new FailException("Account is already existed.", 1000);
        }
    }

    @Override
    @Transactional
    public AccountRole getRoleOfUser(String email) {
        Optional<Account> account = accountRepository.findByEmail(email);
        return account.get().getRole().getName();
    }

    @Override
    public void updatePasswordCustomer(ChangePassReq changePassReq, Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found", 1054));
        Account account = accountRepository.findByCustomerId(customer.getId())
                .orElseThrow(() -> new NotFoundException("Account not found", 1055));
        if (account.getPassword().equals(changePassReq.getOldPassword())) {
            account.setPassword(changePassReq.getNewPassword());
            accountRepository.save(account);
        } else {
            throw new FailException("Password incorrect.", 1111);
        }
    }

    @Override
    public void deleteCustomer(Integer customerId) {
        Customer customer = customerRepository.findById(customerId).
                orElseThrow(() -> new NotFoundException("Customer not found", 1054));
        customer.setDelete(true);
        try {
            customerRepository.save(customer);
        } catch (Exception e) {
            throw new FailException("Can't delete customer", 1059);
        }
    }

    @Override
    public String changeAvatarCustomer(EditAvatarCus editAvatarCus) {
        Customer customer = customerRepository.findById(editAvatarCus.getCustomerId()).
                orElseThrow(() -> new NotFoundException("Customer not found", 1060));
        Account account = accountRepository.findById(customer.getAccount().getId()).
                orElseThrow(() -> new NotFoundException("Account not found", 1061));

        account.setAvatar(editAvatarCus.getLink());
        try {
            accountRepository.save(account);
        }catch (Exception e) {
            throw new FailException("Can't update avatar", 1059);
        }
        return account.getEmail();
    }
}
