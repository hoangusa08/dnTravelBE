package com.example.dnTravelBE.repository;

import com.example.dnTravelBE.entity.ConfirmationCodeEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationCodeMailRepository extends JpaRepository<ConfirmationCodeEmail, Integer> {
    Optional<ConfirmationCodeEmail> findByAccount_Id( Integer accountId);
}
