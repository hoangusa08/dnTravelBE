package com.example.dnTravelBE.controller;

import com.example.dnTravelBE.dto.LoginRequestDto;
import com.example.dnTravelBE.dto.RegisterCustomerDto;
import com.example.dnTravelBE.service.AccountService;
import com.example.dnTravelBE.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authen")
@AllArgsConstructor
public class LoginController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final AccountService accountService;

    @PostMapping("/login")
    public String genereToken(@RequestBody LoginRequestDto loginRequestDto) throws  Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
            );
        } catch (Exception e){
            throw new Exception("Invalid Email or Password");
        }
        return jwtUtil.generateToken(loginRequestDto.getEmail());
    }
    @PostMapping("/register/customer")
    public ResponseEntity<Object> register(@RequestBody RegisterCustomerDto registerCustomerDto){
        return accountService.createAccountCustomer(registerCustomerDto);
    }
}
