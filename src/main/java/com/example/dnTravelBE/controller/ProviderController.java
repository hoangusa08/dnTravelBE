package com.example.dnTravelBE.controller;

import com.example.dnTravelBE.dto.ResponseDto;
import com.example.dnTravelBE.dto.TourDto;
import com.example.dnTravelBE.service.TourService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Provider")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProviderController {

    private final TourService tourService;
    @GetMapping("tours")
    private ResponseEntity<Object> getTours() {
        return null;
    }

    @PostMapping("createTour")
    private ResponseEntity<Object> createTour(@RequestBody TourDto tourDto) {
        boolean status = tourService.createTour(tourDto);
        return ResponseEntity.ok(ResponseDto.responseWithoutData());
    }

    @PostMapping("editTour")
    private ResponseEntity<Object> editTour() {
        return null;
    }

    @PostMapping("deteleTour")
    private ResponseEntity<Object> deteleTours() {
        return null;
    }

    @GetMapping("bookTousComplete")
    private ResponseEntity<Object> getBookToursComplete() {
        return null;
    }

    @GetMapping("bookToursWait")
    private ResponseEntity<Object> getBookToursWait() {
        return null;
    }


}
