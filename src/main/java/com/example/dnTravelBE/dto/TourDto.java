package com.example.dnTravelBE.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TourDto {

    private String name;

    private Integer adultPrice ;

    private Integer childPrice ;

    private String description ;

    private Integer providerId;

    private String status;

    private ArrayList<Date> schedules;
}
