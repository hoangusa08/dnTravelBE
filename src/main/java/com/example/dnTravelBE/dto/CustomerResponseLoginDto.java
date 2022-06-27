package com.example.dnTravelBE.dto;

import com.example.dnTravelBE.constant.AccountRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseLoginDto {

    private String address;
    private String fullName;
    private Integer id;
    private String phoneNumber;
    private String role;
    private String username;
    private String email;
    private String avatar;
    private boolean isDelete;
}
