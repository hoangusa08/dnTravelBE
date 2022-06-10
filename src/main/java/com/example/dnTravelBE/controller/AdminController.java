package com.example.dnTravelBE.controller;

import com.example.dnTravelBE.constant.StatusEnum;
import com.example.dnTravelBE.dto.ResponseDto;
import com.example.dnTravelBE.service.ProviderService;
import com.example.dnTravelBE.service.TourService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {

    private final ProviderService providerService;
    private final TourService tourService;

    @GetMapping("provider/{status}")
    private ResponseEntity<Object> getProvidersAccept(@RequestParam(defaultValue = "0") @Min(0) Integer page,
                                                      @RequestParam(defaultValue = "") String keyword,
                                                      @PathVariable("status") StatusEnum status) {
        return ResponseEntity.ok(ResponseDto.response(providerService.getAllProviderByStatus(status, page, keyword)));
    }

    @PostMapping("/provider/{status}/{id}")
    private ResponseEntity<Object> acceptProvider(@PathVariable("id") Integer id, @PathVariable("status") StatusEnum status) {
        providerService.changeStatusProvider(id, status);
        return ResponseEntity.ok(ResponseDto.responseWithoutData());
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

    @GetMapping("toursAwait")
    public ResponseEntity<Object> getAllTourAwait(@RequestParam(defaultValue = "0") @Min(0) Integer page,
                                                  @RequestParam(defaultValue = "") String keyword) {
        return ResponseEntity.ok(ResponseDto.response(tourService.getAllTour(StatusEnum.WAITING, page, keyword)));
    }

    @GetMapping("toursRefuse")
    public ResponseEntity<Object> getAllTourRefuse(@RequestParam(defaultValue = "0") @Min(0) Integer page,
                                                   @RequestParam(defaultValue = "") String keyword) {
        return ResponseEntity.ok(ResponseDto.response(tourService.getAllTour(StatusEnum.REFUSE, page, keyword)));
    }

    @PostMapping("refuse-tour/{id}")
    private ResponseEntity<Object> refuseTour(@PathVariable Integer id) {
        tourService.changeStatusTour(id, StatusEnum.REFUSE);
        return ResponseEntity.ok(ResponseDto.responseWithoutData());
    }

    @GetMapping("toursAccept")
    public ResponseEntity<Object> getAllTourAccept(@RequestParam(defaultValue = "0") @Min(0) Integer page,
                                                   @RequestParam(defaultValue = "") String keyword) {
        return ResponseEntity.ok(ResponseDto.response(tourService.getAllTour(StatusEnum.ACCEPT, page, keyword)));
    }

    @PostMapping("accept-tour/{id}")
    private ResponseEntity<Object> acceptTour(@PathVariable Integer id) {
        tourService.changeStatusTour(id, StatusEnum.ACCEPT);
        return ResponseEntity.ok(ResponseDto.responseWithoutData());
    }
}
