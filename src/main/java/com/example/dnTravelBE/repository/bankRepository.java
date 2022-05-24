package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface bankRepository extends JpaRepository<Bank, Integer> {

    Optional<Bank> findBankById (Integer bankId);
}
