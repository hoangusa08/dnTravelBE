package com.example.dnTravelBE.controller;

import com.example.dnTravelBE.constant.StatusEnum;
import com.example.dnTravelBE.dto.ResponseDto;
import com.example.dnTravelBE.service.ProviderService;
import com.example.dnTravelBE.service.TourService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {

    private final ProviderService providerService;
    private final TourService tourService;

    @GetMapping("providerAccept")
    private ResponseEntity<Object> getProvidersAccept() {
        return null;
    }

    @PostMapping("/accept-provider/{id}")
    private ResponseEntity<Object> acceptProvider(@PathVariable Integer id) {
        providerService.changeStatusProvider(id, StatusEnum.ACCEPT);
        return ResponseEntity.ok(ResponseDto.responseWithoutData());
    }

    @GetMapping("providerRefuse")
    private ResponseEntity<Object> getProvidersRefuse() {
        return null;
    }

    @PostMapping("/refuse-provider/{id}")
    private ResponseEntity<Object> refuseProvider(@PathVariable Integer id) {
        providerService.changeStatusProvider(id, StatusEnum.REFUSE);
        return ResponseEntity.ok(ResponseDto.responseWithoutData());
    }

    @GetMapping("providerWait")
    private ResponseEntity<Object> getProvidersWait() {
        return null;
    }

    @GetMapping("customerAccept")
    private ResponseEntity<Object> getCustomersAccept() {
        return null;
    }

    @GetMapping("customerRefuse")
    private ResponseEntity<Object> getCustomerRefuse() {
        return null;
    }

    @GetMapping("customerWait")
    private ResponseEntity<Object> getCustomerWait() {
        return null;
    }

    @GetMapping("tourWait")
    private ResponseEntity<Object> getToursWait() {
        return null;
    }

    @GetMapping("tourRefuse")
    private ResponseEntity<Object> getToursRefuse() {
        return null;
    }

    @PostMapping("refuse-tour/{id}")
    private ResponseEntity<Object> refuseTour(@PathVariable Integer id) {
        tourService.changeStatusTour(id, StatusEnum.REFUSE);
        return ResponseEntity.ok(ResponseDto.responseWithoutData());
    }

    @GetMapping("toursAccept")
    private ResponseEntity<Object> getToursAccept() {
        return null;
    }

    @PostMapping("accept-tour/{id}")
    private ResponseEntity<Object> acceptTour(@PathVariable Integer id) {
        tourService.changeStatusTour(id, StatusEnum.ACCEPT);
        return ResponseEntity.ok(ResponseDto.responseWithoutData());
    }
}
