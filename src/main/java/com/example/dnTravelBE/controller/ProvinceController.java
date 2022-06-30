package com.example.dnTravelBE.controller;

import com.example.dnTravelBE.dto.ResponseDto;
import com.example.dnTravelBE.service.ProvinceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/province")
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProvinceController {

    private final ProvinceService provinceService;

    @GetMapping()
    public ResponseEntity<Object> getProvinces () {
        return ResponseEntity.ok(ResponseDto.response(provinceService.getProvinces()));
    }

}
