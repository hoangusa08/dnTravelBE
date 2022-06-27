package com.example.dnTravelBE.controller;

import com.example.dnTravelBE.constant.StatusEnum;
import com.example.dnTravelBE.dto.ResponseDto;
import com.example.dnTravelBE.service.TourService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@RequestMapping("/tour")
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class TourController {

    private final TourService tourService;

    @GetMapping()
    public ResponseEntity<Object> getAllTourAccept(@RequestParam(defaultValue = "0") @Min(0) Integer page,
                                                   @RequestParam(defaultValue = "") String keyword) {
        return ResponseEntity.ok(ResponseDto.response(tourService.getAllTour(StatusEnum.ACCEPT, page, keyword)));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getTourDeTail(@PathVariable int id) throws Exception {
        return ResponseEntity.ok(ResponseDto.response(tourService.getTourDetailById(id)));
    }

    @GetMapping("/new-tours")
    public ResponseEntity<Object> getNewTours() throws Exception {
        return null;
    }

    @GetMapping("list-rate-tour")
    public ResponseEntity<Object> getRateTour() {
        return null;
    }


    @GetMapping("/dasdboard/tour")
    public ResponseEntity<Object> getToursDashboard(){
        return tourService.getToursDashboard();
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Object> getToursByCate(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(ResponseDto.response(tourService.getAllTourByCategory(id)));
    }

    @GetMapping("/province/{id}")
    public ResponseEntity<Object> getToursByProvince(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(ResponseDto.response(tourService.getAllTourByProvince(id)));
    }
}