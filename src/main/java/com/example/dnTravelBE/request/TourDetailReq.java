package com.example.dnTravelBE.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TourDetailReq {

    private String name;

    private Integer adultPrice;

    private Integer childPrice;

    private String description;

    private Integer providerId;

    private Integer provinceId;

    private String subDescription;

    private Integer categoryTd;

    private Integer numberDate;

    private Integer numberPeople;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private ArrayList<LocalDate> schedules;

    private ArrayList<String> tourImage;
}
