package com.example.dnTravelBE.controller;

import com.example.dnTravelBE.constant.StatusEnum;
import com.example.dnTravelBE.dto.ResponseDto;
import com.example.dnTravelBE.dto.ResponseTourListDto;
import com.example.dnTravelBE.dto.TourDto;
import com.example.dnTravelBE.service.TourService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@RestController
@RequestMapping("/provider")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProviderController {

    private final TourService tourService;

    @GetMapping("tours/{id}/{status}")
    private ResponseEntity<Object> getTours(@PathVariable("status") StatusEnum status,
                                            @PathVariable("id") Integer id,
                                            @RequestParam(defaultValue = "0") @Min(0) Integer page,
                                            @RequestParam(defaultValue = "") String keyword) {
        ResponseTourListDto responseTourListDto = tourService.getAllTourProvider(id, status, page, keyword);
        return ResponseEntity.ok(ResponseDto.response(responseTourListDto));
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

    @PostMapping("deteleTour/{id}/{status}")
    private ResponseEntity<Object> deteleTours(@PathVariable("status") boolean status, @PathVariable("id") Integer id) {
        tourService.setStatusTour(id, status);
        return ResponseEntity.ok(ResponseDto.responseWithoutData());
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
