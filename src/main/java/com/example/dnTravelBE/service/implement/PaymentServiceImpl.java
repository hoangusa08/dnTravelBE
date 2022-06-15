package com.example.dnTravelBE.service.implement;

import com.example.dnTravelBE.dto.PaymentsDto;
import com.example.dnTravelBE.entity.Payment;
import com.example.dnTravelBE.exception.FailException;
import com.example.dnTravelBE.exception.NotFoundException;
import com.example.dnTravelBE.mapper.PaymentMapper;
import com.example.dnTravelBE.repository.PaymentRepo;
import com.example.dnTravelBE.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepo paymentRepo;

    @Override
    public List<PaymentsDto> getAllTourCusTomerByStatus(String status, Integer customerId) {
        List<Payment> payments = paymentRepo.findByCustomerIdAndStatus(customerId, status);
        List<PaymentsDto> paymentsDtos = new ArrayList<>();
        for (Payment payment : payments) {
            PaymentsDto paymentsDto = PaymentMapper.toPaymentsDto(payment);
            paymentsDtos.add(paymentsDto);
        }
        return paymentsDtos;
    }

    @Override
    public List<PaymentsDto> getAllTourProviderByStatus(String status, Integer providerId) {
        List<Payment> payments = paymentRepo.findByProviderIdAndStatus(providerId, status);
        List<PaymentsDto> paymentsDtos = new ArrayList<>();
        for (Payment payment : payments) {
            PaymentsDto paymentsDto = PaymentMapper.toPaymentsDto(payment);
            paymentsDtos.add(paymentsDto);
        }

        return paymentsDtos;
    }

    @Override
    public void changeStatusPayment(Integer bookId, String status) {
        Payment payment = paymentRepo.findById(bookId)
                .orElseThrow(() -> new NotFoundException("booked not found" , 1063));
        payment.setStatus(status);
        try {
            paymentRepo.save(payment);
        }catch (Exception e) {
            throw new FailException("Cann't change status", 2022);
        }
    }
}
