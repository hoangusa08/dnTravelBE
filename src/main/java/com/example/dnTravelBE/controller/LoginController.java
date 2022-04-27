package com.example.dnTravelBE.controller;

import com.example.dnTravelBE.constant.AccountRole;
import com.example.dnTravelBE.dto.AcceptCodeDto;
import com.example.dnTravelBE.dto.LoginRequestDto;
import com.example.dnTravelBE.dto.RegisterCustomerDto;
import com.example.dnTravelBE.dto.ResponseDto;
import com.example.dnTravelBE.entity.Account;
import com.example.dnTravelBE.exception.FailException;
import com.example.dnTravelBE.service.AccountService;
import com.example.dnTravelBE.service.CustomerService;
import com.example.dnTravelBE.service.EmailService;
import com.example.dnTravelBE.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/authen")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class LoginController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final AccountService accountService;
    private final CustomerService customerService;
    private final EmailService emailService;

    @GetMapping("/abc")
    public ResponseEntity<Object> abc () {
        return ResponseEntity.ok("abc");
    }
    @PostMapping("/login/customer")
    public ResponseEntity<Object> generalToken(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Invalid Email or Password");
        }
        AccountRole role = accountService.getRoleOfUser(loginRequestDto.getEmail());
        System.out.println(role);
//        if(role != AccountRole.ROLE_CUSTOMER) {
//            throw new FailException("Role Invalid", 1000);
//        }
        Map<String, Object> response = new HashMap<>();
        response.put("access_token", jwtUtil.generateToken(loginRequestDto.getEmail()));
        response.put("message", "login successfully");
        response.put("result", true);
        response.put( "status" , 200);
        response.put("user", customerService.getCustomerWhenLogin(loginRequestDto.getEmail()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register/customer")
    public ResponseEntity<Object> registerCustomer(@RequestBody RegisterCustomerDto registerCustomerDto) throws MessagingException {
        Account account = accountService.createAccountCustomer(registerCustomerDto);
//        emailService.sendCodeVerifyMail(account);
        return ResponseEntity.ok(ResponseDto.responseWithoutData());
    }

    @PostMapping("/register/provider")
    public ResponseEntity<Object> registerProvider(@RequestBody RegisterCustomerDto registerCustomerDto) throws MessagingException {
        Account account = accountService.createAccountCustomer(registerCustomerDto);
//        emailService.sendCodeVerifyMail(account);
        return ResponseEntity.ok(ResponseDto.responseWithoutData());
    }

    @GetMapping("/resend-email/{accountId}")
    public ResponseEntity<Object> resendEmail(@PathVariable Integer accountId) throws MessagingException {
        emailService.resendEmail(accountId);
        return ResponseEntity.ok(ResponseDto.responseWithoutData());
    }

    @PostMapping("/accept-code")
    public ResponseEntity<Object> acceptCode(@RequestBody AcceptCodeDto acceptCodeDto) {
        return emailService.acceptCode(acceptCodeDto);
    }

}
