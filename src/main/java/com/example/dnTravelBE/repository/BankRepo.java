package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface BankRepo extends JpaRepository<Bank, Integer> {

    List<Bank> getAllBy();
    Optional<Bank> findBankById( Integer id);
}
