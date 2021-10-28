package com.example.dnTravelBE.exception;

import lombok.Getter;

@Getter
public class FailException extends RuntimeException {

    private Integer DevCode;

    public FailException(String message, Integer devCode) {
        super(message);
        this.DevCode = devCode;
    }
}
