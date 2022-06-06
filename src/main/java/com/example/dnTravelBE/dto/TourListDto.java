package com.example.dnTravelBE.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;
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

public class TourListDto {

    private Integer id;

    private String name;

    private Integer adultPrice;

    private Integer childPrice;

    private String description;

    private String provider;

    private String province;

    private String category;

    private String tourImage;

    private Integer star;
}
