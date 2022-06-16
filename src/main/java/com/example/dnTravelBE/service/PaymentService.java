package com.example.dnTravelBE.service;

import com.example.dnTravelBE.dto.PaymentsDto;
import com.example.dnTravelBE.request.PaymentTourReq;

import java.util.List;

public interface PaymentService {

    List<PaymentsDto> getAllTourCusTomerByStatus(String status, Integer customerId) ;

    List<PaymentsDto> getAllTourProviderByStatus( String status, Integer providerId) ;

    void changeStatusPayment ( Integer bookId, String status);

    void paymentTour (PaymentTourReq paymentTourReq);

}
