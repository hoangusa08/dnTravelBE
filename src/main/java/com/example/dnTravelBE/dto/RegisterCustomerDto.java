package com.example.dnTravelBE.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCustomerDto {

    private String username;
    @Email
    private String email;
    private String password;
    private String fullname;
    private String address;
    private String phoneNumber;
}
