package com.example.dnTravelBE.controller;

import com.example.dnTravelBE.constant.StatusEnum;
import com.example.dnTravelBE.dto.PaymentsDto;
import com.example.dnTravelBE.dto.ResponseDto;
import com.example.dnTravelBE.dto.ResponseTourListDto;
import com.example.dnTravelBE.dto.TourDto;
import com.example.dnTravelBE.service.PaymentService;
import com.example.dnTravelBE.service.TourService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/provider")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProviderController {

    private final TourService tourService;

    private final PaymentService paymentService;

    @GetMapping("tours/{id}/{status}")
    private ResponseEntity<Object> getTours(@PathVariable("status") StatusEnum status,
                                            @PathVariable("id") Integer id,
                                            @RequestParam(defaultValue = "0") @Min(0) Integer page,
                                            @RequestParam(defaultValue = "") String keyword) {
        ResponseTourListDto responseTourListDto = tourService.getAllTourProvider(id, status, page, keyword, false);
        return ResponseEntity.ok(ResponseDto.response(responseTourListDto));
    }

    @GetMapping("toursDelete/{providerId}")
    private ResponseEntity<Object> getToursDelete(@PathVariable("providerId") Integer providerId,
            @RequestParam(defaultValue = "0") @Min(0) Integer page,
            @RequestParam(defaultValue = "") String keyword) {
        ResponseTourListDto responseTourListDto = tourService.getAllTourDelete(providerId , page, keyword );
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

    @GetMapping("/list-book-tour/{providerId}/{status}")
    public ResponseEntity<Object> getListBookTour(@PathVariable("status") String status, @PathVariable("providerId") Integer providerId) {
        List<PaymentsDto> paymentsDtos = paymentService.getAllTourProviderByStatus( status, providerId);
        return ResponseEntity.ok(ResponseDto.response(paymentsDtos));
    }

    @PostMapping("/book-tour/{id}/{status}")
    private ResponseEntity<Object> changeStatusBook(@PathVariable("status") String status,
                                                    @PathVariable("id") Integer id) {
        paymentService.changeStatusPayment(id, status);
        return ResponseEntity.ok(ResponseDto.responseWithoutData());
    }


}
