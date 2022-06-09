package com.example.dnTravelBE.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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

    private Integer adultPrice;

    private Integer childPrice;

    private String description;

    private Integer providerId;

    private Integer provinceId;

    private String subDescription;

    private Integer categoryTd;

    private Integer numberDate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private ArrayList<LocalDate> schedules;

    private ArrayList<String> tourImage;
}
