package com.example.dnTravelBE.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TourDetailDto {

    private String name;
    private Integer adultPrice;
    private Integer childPrice;
    private String description;
    private ArrayList<String> tourImage;
    private ArrayList<Date> schedules;
    private ArrayList<RateDto> rateDtos;
}
