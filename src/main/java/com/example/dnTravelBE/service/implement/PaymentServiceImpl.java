package com.example.dnTravelBE.service.implement;

import com.example.dnTravelBE.dto.PaymentsDto;
import com.example.dnTravelBE.entity.Customer;
import com.example.dnTravelBE.entity.Payment;
import com.example.dnTravelBE.entity.Schedule;
import com.example.dnTravelBE.entity.Tour;
import com.example.dnTravelBE.exception.FailException;
import com.example.dnTravelBE.exception.NotFoundException;
import com.example.dnTravelBE.mapper.PaymentMapper;
import com.example.dnTravelBE.repository.CustomerRepository;
import com.example.dnTravelBE.repository.PaymentRepo;
import com.example.dnTravelBE.repository.ScheduleRepo;
import com.example.dnTravelBE.repository.TourRepo;
import com.example.dnTravelBE.request.PaymentTourReq;
import com.example.dnTravelBE.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepo paymentRepo;
    private final TourRepo tourRepo;
    private final CustomerRepository customerRepository;

    private final ScheduleRepo scheduleRepo;

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

    @Override
    public void paymentTour(PaymentTourReq paymentTourReq) {
        Customer customer = customerRepository.findById(paymentTourReq.getCustomerId())
                .orElseThrow(() -> new NotFoundException("customer not found" , 1064));
        Tour tour = tourRepo.findById(paymentTourReq.getTourId())
                .orElseThrow(() -> new NotFoundException("tour not found" , 1064));
        Schedule schedule = scheduleRepo.findById(paymentTourReq.getScheduleId())
                .orElseThrow(() -> new NotFoundException("Schedule not found" , 1065));

        Payment payment = new Payment();
        payment.setAdultNumber(paymentTourReq.getAdultNumber());
        payment.setChildrenNumber(paymentTourReq.getChildrenNumber());
        payment.setStatus("WAITING");
        payment.setTotal(paymentTourReq.getTotal());
        payment.setSchedule(schedule);
        payment.setTour(tour);
        payment.setCustomer(customer);
        payment.setProvider(tour.getProvider());
        payment.setCreateAt(LocalDate.now());
        try {
            paymentRepo.save(payment);
        }catch (Exception e) {
            throw new FailException("Cann't create payment", 2029);
        }
    }
}
