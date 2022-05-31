package com.example.dnTravelBE.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Provider")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProviderController {

    @GetMapping("tours")
    private ResponseEntity<Object> getTours() {
        return null;
    }

    @PostMapping("createTour")
    private ResponseEntity<Object> createTour() {
        return null;
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
