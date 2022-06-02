package com.example.dnTravelBE.controller;

import com.example.dnTravelBE.constant.StatusEnum;
import com.example.dnTravelBE.dto.ResponseDto;
import com.example.dnTravelBE.request.RateTourReq;
import com.example.dnTravelBE.service.CustomerService;
import com.example.dnTravelBE.service.TourService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class CustomerController {

    private final TourService tourService;
    private final CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomerDetail(@PathVariable int id) {
        customerService.getCustomerDetail(id);
        return ResponseEntity.ok(ResponseDto.response(""));
    }

    @GetMapping("/edit-detail/{id}")
    public ResponseEntity<Object> getCustomer(@PathVariable int id) {
        return null;
    }

    @GetMapping("/list-book-tour")
    public ResponseEntity<Object> getListBookTour() {
        return null;
    }

    @GetMapping("/list-book-tour-history")
    public ResponseEntity<Object> getListBookTourHistory() {
        return null;
    }

    @GetMapping("tours-watching")
    public ResponseEntity<Object> getToursWatching() {
        return null;
    }

    @PostMapping("tours-watching/{id}")
    public ResponseEntity<Object> addTourWatching() {
        return null;
    }

    @PostMapping("rate-tour")
    public ResponseEntity<Object> rateTour(@RequestBody RateTourReq rateTourReq) {
        tourService.createRateTour(rateTourReq);
        return ResponseEntity.ok(ResponseDto.responseWithoutData());
    }

    @PutMapping("rate-tour")
    public ResponseEntity<Object> EditRateTour() {
        return null;
    }

    @GetMapping("rate-tour")
    public ResponseEntity<Object> getRateTour() {
        return null;
    }

}
