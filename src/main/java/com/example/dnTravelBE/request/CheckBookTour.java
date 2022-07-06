package com.example.dnTravelBE.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckBookTour {
    private Integer customerId;
    private Integer tourId;
    private Integer scheduleId;
}
