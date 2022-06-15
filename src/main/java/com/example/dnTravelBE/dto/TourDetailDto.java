package com.example.dnTravelBE.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TourDetailDto {

    private Integer id;
    private String name;
    private Integer adultPrice;
    private Integer childPrice;
    private String description;
    private List<String> tourImage;
    private List<Object> schedules;
    private List<RateDto> rateTours;
    private Double totalStar;
}
