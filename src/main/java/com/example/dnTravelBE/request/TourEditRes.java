package com.example.dnTravelBE.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourEditRes {

    private String name;
    private Integer adultPrice;
    private Integer childPrice;
    private String description;
    private List<String> tourImage;
    private Integer dateNumber;
    private String subDescription;
    private Integer categoryId;
    private Integer provinceId;

}
