package com.example.dnTravelBE.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateDto {
    private int id;
    private String user_full_name;
    private String comment;
    private LocalDate create_at;
    private String avatar_source;
    private int star;
}
