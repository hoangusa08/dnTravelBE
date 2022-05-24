package com.example.dnTravelBE.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterProviderDto {

    private String username;
    @Email
    private String email;
    private String password;
    private String fullName;
    private String address;
    private String phoneNumber;
}
