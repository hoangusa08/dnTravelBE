package com.example.dnTravelBE.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@RequestMapping("/tour")
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class TourController {

    @GetMapping()
    public ResponseEntity<Object> getAllTourAccept(@RequestParam(defaultValue = "0") @Min(0) Integer page,
                                                   @RequestParam(defaultValue = " ") String keyword) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTourDeTail(@PathVariable int id)throws Exception {
        return null;
    }

    @GetMapping("/new-tours")
    public ResponseEntity<Object> getNewTours()throws Exception {
        return null;
    }

    @GetMapping("list-rate-tour")
    public ResponseEntity<Object> getRateTour( ) {
        return null;
    }
}
