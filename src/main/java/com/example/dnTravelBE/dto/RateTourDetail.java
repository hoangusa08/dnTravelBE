package com.example.dnTravelBE.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateTourDetail {
    private int id;
    private String comment;
    private LocalDate create_at;
    private Integer star;
    private String tourName;
}
