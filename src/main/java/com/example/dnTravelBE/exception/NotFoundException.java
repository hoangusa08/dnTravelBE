package com.example.dnTravelBE.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class NotFoundException extends RuntimeException{
    private Integer DevCode;

    public NotFoundException(String message, Integer devCode) {
        super(message);
        this.DevCode = devCode;
    }
}
