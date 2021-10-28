package com.example.dnTravelBE.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class ExceptionApi {

    private boolean Result;
    private HttpStatus StatusCode;
    private Integer DevCode;
    private String message;

    public ExceptionApi(boolean result, HttpStatus statusCode, Integer devCode, String message) {
        Result = result;
        StatusCode = statusCode;
        DevCode = devCode;
        this.message = message;
    }
}
