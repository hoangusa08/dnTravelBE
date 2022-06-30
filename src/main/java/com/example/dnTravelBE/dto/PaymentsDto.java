package com.example.dnTravelBE.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentsDto {

    private Integer id;
    private Integer tourId;
    private Integer providerId;
    private String providerName;
    private String tourName;
    private Integer customerId;
    private String customerName;
    private String status;
    private Integer total;
    private Integer adultNumber;
    private Integer childrenNumber;
    private LocalDate schedule;
    private String image;

}
