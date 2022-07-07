package com.example.dnTravelBE.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TourNeedComplete {

    private Integer id;
    private Integer tourId;
    private Integer scheduleId;
    private String name;
    private Integer adultNumber;
    private Integer childNumber;
    private LocalDate schedule;
    private String image;
    private List<CustomerInTourNeed> customerInTourNeeds;
}
