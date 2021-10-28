package com.example.dnTravelBE.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(value = FailException.class)
    public ResponseEntity<Object> handleRegistration(HttpServletRequest request,
                                                     FailException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionApi exceptionAPI = new ExceptionApi(
                false,
                httpStatus,
                exception.getDevCode(),
                exception.getMessage()
        );

        return new ResponseEntity<>(exceptionAPI, httpStatus);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleRegistration(HttpServletRequest request,
                                                     HttpMessageNotReadableException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionApi exceptionAPI = new ExceptionApi(
                false,
                httpStatus,
                2901,
                exception.getMessage()
        );

        return new ResponseEntity<>(exceptionAPI, httpStatus);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleRegistration(HttpServletRequest request,
                                                     HttpRequestMethodNotSupportedException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionApi exceptionAPI = new ExceptionApi(
                false,
                httpStatus,
                2902,
                exception.getMessage()
        );

        return new ResponseEntity<>(exceptionAPI, httpStatus);
    }
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleRegistration(HttpServletRequest request,
                                                     MissingServletRequestParameterException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionApi exceptionAPI = new ExceptionApi(
                false,
                httpStatus,
                2903,
                exception.getMessage()
        );

        return new ResponseEntity<>(exceptionAPI, httpStatus);
    }
}
