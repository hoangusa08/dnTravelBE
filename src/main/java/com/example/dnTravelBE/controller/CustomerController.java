package com.example.dnTravelBE.controller;

import com.example.dnTravelBE.dto.CustomerResponseLoginDto;
import com.example.dnTravelBE.dto.PaymentsDto;
import com.example.dnTravelBE.dto.ResponseDto;
import com.example.dnTravelBE.request.ChangePassReq;
import com.example.dnTravelBE.request.PaymentTourReq;
import com.example.dnTravelBE.request.RateTourReq;
import com.example.dnTravelBE.service.AccountService;
import com.example.dnTravelBE.service.CustomerService;
import com.example.dnTravelBE.service.PaymentService;
import com.example.dnTravelBE.service.TourService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class CustomerController {

    private final TourService tourService;
    private final CustomerService customerService;
    private final PaymentService paymentService;
    private final AccountService accountService;


    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomerDetail(@PathVariable int id) {
        CustomerResponseLoginDto customerResponseLoginDto = customerService.getCustomerDetail(id);
        return ResponseEntity.ok(ResponseDto.response(customerResponseLoginDto));
    }

    @PutMapping("/edit-detail/{id}")
    public ResponseEntity<Object> getCustomer(@PathVariable int id, @RequestBody CustomerResponseLoginDto customerResponseLoginDto) {
        customerService.updateCustomerDetail(customerResponseLoginDto);
        return ResponseEntity.ok(ResponseDto.responseWithoutData());
    }

    @GetMapping("/list-book-tour/{customerId}/{status}")
    public ResponseEntity<Object> getListBookTour(@PathVariable("status") String status, @PathVariable("customerId") Integer customerId) {
        List<PaymentsDto> paymentsDtos = paymentService.getAllTourCusTomerByStatus( status, customerId);
        return ResponseEntity.ok(ResponseDto.response(paymentsDtos));
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

    @GetMapping("rate-tour/{customerId}/{tourId}")
    public ResponseEntity<Object> getRateTour() {
        return null;
    }

    @PostMapping("/payment")
    public ResponseEntity<Object> payment(@RequestBody PaymentTourReq paymentTourReq) {
        paymentService.paymentTour(paymentTourReq);
        return ResponseEntity.ok(ResponseDto.responseWithoutData());
    }

    @PutMapping("/change-password/{id}")
    public ResponseEntity<Object> changePass(@PathVariable("id") Integer id, @RequestBody ChangePassReq changePassReq) {
        accountService.updatePasswordCustomer(changePassReq, id);
        return ResponseEntity.ok(ResponseDto.responseWithoutData());
    }

}
