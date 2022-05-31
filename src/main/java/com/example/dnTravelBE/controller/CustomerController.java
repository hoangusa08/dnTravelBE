package com.example.dnTravelBE.controller;

import com.example.dnTravelBE.constant.StatusEnum;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Customer")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class CustomerController {

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomerDetail(@PathVariable int id) {
        return null;
    }

    @GetMapping("/edit-detail/{id}")
    public ResponseEntity<Object> getCustomer(@PathVariable int id) {
        return null;
    }

    @GetMapping("/list-book-tour")
    public ResponseEntity<Object> getListBookTour(){
        return null;
    }

    @GetMapping("/list-book-tour-history")
    public ResponseEntity<Object> getListBookTourHistory(){
        return null;
    }

    @GetMapping("tours-watching")
    public ResponseEntity<Object> getToursWatching () {
        return null;
    }

    @PostMapping("tours-watching/{id}")
    public ResponseEntity<Object> addTourWatching () {
        return null;
    }

    @PostMapping("rate-tour")
    public ResponseEntity<Object> rateTour () {
        return null;
    }

    @PutMapping("rate-tour")
    public ResponseEntity<Object> EditRateTour () {
        return null;
    }

    @GetMapping("rate-tour")
    public ResponseEntity<Object> getRateTour( ) {
        return null;
    }

}
