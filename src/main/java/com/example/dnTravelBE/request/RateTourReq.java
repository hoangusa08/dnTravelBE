package com.example.dnTravelBE.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateTourReq {

    private Integer customerId;
    private Integer star;
    private String comment;
    private Integer tourId;
}
