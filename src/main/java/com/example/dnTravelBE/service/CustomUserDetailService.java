package com.example.dnTravelBE.service;

import com.example.dnTravelBE.entity.Account;
import com.example.dnTravelBE.entity.Role;
import com.example.dnTravelBE.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByEmail(email);
        List<GrantedAuthority> authenAccount = new ArrayList<>();
        authenAccount.add(new SimpleGrantedAuthority(account.get().getRole().getName().toString()));
        return  new org.springframework.security.core.userdetails.User(account.get().getEmail() , account.get().getPassword(), new ArrayList<>());
    }
}
