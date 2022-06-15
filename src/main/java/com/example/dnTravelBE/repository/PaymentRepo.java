package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PaymentRepo extends JpaRepository<Payment, Integer> {

    List<Payment> findByCustomerIdAndStatus ( Integer customerId , String status);

    List<Payment> findByProviderIdAndStatus ( Integer providerId, String status);

    Optional<Payment> findById (Integer id);
}
