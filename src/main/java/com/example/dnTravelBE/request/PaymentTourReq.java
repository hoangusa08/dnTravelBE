package com.example.dnTravelBE.request;

import com.example.dnTravelBE.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTourReq {

    private Integer adultNumber;

    private Integer childrenNumber;

    private Integer total;

    private Integer scheduleId;

    private Integer tourId;

    private Integer customerId;

}
