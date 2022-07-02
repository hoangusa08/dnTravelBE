package com.example.dnTravelBE.controller;

import com.example.dnTravelBE.constant.StatusEnum;
import com.example.dnTravelBE.dto.PaymentsDto;
import com.example.dnTravelBE.dto.ResponseDto;
import com.example.dnTravelBE.dto.ResponseTourListDto;
import com.example.dnTravelBE.request.TourDetailReq;
import com.example.dnTravelBE.request.ProviderReq;
import com.example.dnTravelBE.request.TourEditRes;
import com.example.dnTravelBE.service.PaymentService;
import com.example.dnTravelBE.service.ProviderService;
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
    private final ProviderService providerService;
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
    private ResponseEntity<Object> createTour(@RequestBody TourDetailReq tourDetailReq) {
        boolean status = tourService.createTour(tourDetailReq);
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

    @GetMapping("tour-detail/{id}")
    private ResponseEntity<Object> getTourDetail(@PathVariable("id") Integer id){
        return ResponseEntity.ok(ResponseDto.response(tourService.getTourDetailById(id)));
    }

    @PutMapping("tour-detail/{id}")
    private ResponseEntity<Object> editTour(@PathVariable("id") Integer id,
                                            @RequestBody TourEditRes tourEditRes){
        tourService.editTour(id, tourEditRes);
        return ResponseEntity.ok(ResponseDto.responseWithoutData());
    }

    @GetMapping("dashboard/{providerId}")
    private ResponseEntity<Object> totalDashboard(@PathVariable("providerId") Integer providerId ){
        return providerService.getTotalDashboard(providerId);
    }

    @GetMapping("dashboard/chart/{providerId}/{year}")
    private ResponseEntity<Object> chartDashboard(@PathVariable("providerId") Integer providerId, @PathVariable("year") int year ){
        return providerService.getChatPaymentDashboard(providerId, year);
    }

    @GetMapping("detail/{id}")
    private ResponseEntity<Object> getProviderDetail(@PathVariable Integer id) {
        return ResponseEntity.ok(ResponseDto.response(providerService.getDetail(id)));
    }

    @PutMapping("/edit-detail")
    private ResponseEntity<Object> editProvider(@RequestBody ProviderReq providerReq){
        providerService.editProvider(providerReq);
        return ResponseEntity.ok(ResponseDto.responseWithoutData());
    }
}
