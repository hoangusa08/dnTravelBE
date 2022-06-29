package com.example.dnTravelBE.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProviderReq {

    private String username;
    private String email;
    private String nameCompany;
    private String address;
    private String phoneNumber;
    private Integer bankId;
    private String bankNumber;
    private String owner;
    private String password;
    private Integer id;
}
