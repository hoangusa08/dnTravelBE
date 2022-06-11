package com.example.dnTravelBE.controller;

import com.example.dnTravelBE.dto.ResponseDto;
import com.example.dnTravelBE.service.BankService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class BankController {

    private final BankService  bankService;

    @GetMapping()
    public ResponseEntity getAllBank(){
        return ResponseEntity.ok(ResponseDto.response(bankService.getAll()));
    }
}
