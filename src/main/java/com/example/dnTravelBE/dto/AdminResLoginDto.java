package com.example.dnTravelBE.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminResLoginDto {

    private String nameConpany;
    private String owner;
    private Integer id;
    private String phoneNumber;
    private String role;
    private String address;
    private String bankNumber;

}
