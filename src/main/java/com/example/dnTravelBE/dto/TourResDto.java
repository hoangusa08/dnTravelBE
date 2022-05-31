package com.example.dnTravelBE.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TourResDto {

    private Integer id ;
    private String name;
    private String description;
    private String provider;
    private String startLocation;
    private String category;
    private String tourImage;
}
