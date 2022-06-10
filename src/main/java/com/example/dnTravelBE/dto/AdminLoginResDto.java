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
public class AdminLoginResDto {

   private Integer id;

   private AccountRole role;

}
