package com.example.dnTravelBE.mapper;

import com.example.dnTravelBE.dto.PaymentsDto;
import com.example.dnTravelBE.entity.Payment;
import com.example.dnTravelBE.entity.TourImage;

public class PaymentMapper {

    public static PaymentsDto toPaymentsDto (Payment payment) {
        PaymentsDto paymentsDto = new PaymentsDto();
        paymentsDto.setId(payment.getId());
        paymentsDto.setTourId(payment.getTour().getId());
        paymentsDto.setProviderId(payment.getProvider().getId());
        paymentsDto.setProviderName(payment.getProvider().getNameCompany());
        paymentsDto.setTourName(payment.getTour().getName());
        paymentsDto.setCustomerId(payment.getCustomer().getId());
        paymentsDto.setCustomerName(payment.getCustomer().getFullName());
        paymentsDto.setStatus(payment.getStatus());
        paymentsDto.setSchedule(payment.getSchedule().getDate());
        paymentsDto.setTotal(payment.getTotal());
        paymentsDto.setAdultNumber(payment.getAdultNumber());
        paymentsDto.setChildrenNumber(payment.getChildrenNumber());
//        for (TourImage tourImage : payment.getTour().getTourImages())
        paymentsDto.setImage(payment.getTour().getTourImages().iterator().next().getLink());
        return paymentsDto;
    }
}
