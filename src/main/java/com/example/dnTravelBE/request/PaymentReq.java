package com.example.dnTravelBE.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentReq {

    private Integer tourId;

    private Integer customerId;

    private Integer adultNumber;

    private Integer childrenNumber;

    private String status;
}
