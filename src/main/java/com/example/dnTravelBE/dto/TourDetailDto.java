package com.example.dnTravelBE.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Date;

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
    private List<LocalDate> schedules;
    private List<RateDto> rateTours;
}
