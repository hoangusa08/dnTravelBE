package com.example.dnTravelBE.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class customerResponseLoginDto {

    private String address;
    private String fullname;
    private Integer id;
    private String phoneNumber;
}
